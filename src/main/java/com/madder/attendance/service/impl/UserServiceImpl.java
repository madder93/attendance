package com.madder.attendance.service.impl;

import com.madder.attendance.Constants;
import com.madder.attendance.bean.TUser;
import com.madder.attendance.bean.vo.UserSessionVo;
import com.madder.attendance.common.RandomGenerate;
import com.madder.attendance.common.Utils;
import com.madder.attendance.dao.UserDao;
import com.madder.attendance.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 用户service实现类
 * @Author wangqian
 * @Date 2019-09-18 9:29
 **/

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public UserSessionVo login(TUser tUser, String sessionId) throws Exception {
        if(StringUtils.isBlank(sessionId)){
            sessionId = RandomGenerate.getRandomChars(32);
        }

        String password = Utils.getMd5Str(tUser.getPassword());
        tUser.setPassword(password);
        TUser user = userDao.getUserByCond(tUser);
        if(user == null){
            throw new Exception("姓名或密码不正确");
        }

        //组装返回信息
        UserSessionVo userSessionVo = new UserSessionVo();
        userSessionVo.setSessionId(sessionId);
        userSessionVo.setUser(user);

        //把session存到redis
        setSessionToRedis(userSessionVo);

        return userSessionVo;

    }

    /**
     * 设置用户session到redis
     * @param userSessionVo
     */
    public void setSessionToRedis(UserSessionVo userSessionVo){
        RedisConnection redisConnection = null;
        try {
            redisConnection = jedisConnectionFactory.getConnection();

            Integer userId = userSessionVo.getUser().getUserId();
            String uniquenessId = "attendance_user_id" + userId.toString();
            byte[] uniquenessIdByte = redisTemplate.getStringSerializer().serialize(uniquenessId);
            byte[] sessionIdByte = redisTemplate.getStringSerializer().serialize(Constants.SESSION_REDIS_PREIX + userSessionVo.getSessionId());
            redisConnection.set(uniquenessIdByte, sessionIdByte);

            JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
            byte[] userSessionByte = serializer.serialize(userSessionVo);
            redisConnection.set(sessionIdByte, userSessionByte);

            redisConnection.expire(uniquenessIdByte, 1800);  //秒为单位
            redisConnection.expire(sessionIdByte, 1800);  //秒为单位
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(redisConnection != null){
                redisConnection.close();
            }
        }

    }

    @Override
    public UserSessionVo getSession(String sessionId) {
        UserSessionVo userSessionVo = getUserSessionBySessionId(sessionId);
        return userSessionVo;
    }

    /**
     * 根据sessionId在redis里获取userSessionVo
     * @param sessionId
     * @return
     */
    public UserSessionVo getUserSessionBySessionId(String sessionId){

        RedisConnection redisConnection = null;
        try {
            redisConnection = jedisConnectionFactory.getConnection();

            byte[] sessionIdByte = redisTemplate.getStringSerializer().serialize(Constants.SESSION_REDIS_PREIX + sessionId);
            if(redisConnection.exists(sessionIdByte)) {
                byte[] userSessionByte = redisConnection.get(sessionIdByte);

                JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
                UserSessionVo userSessionVo = (UserSessionVo) serializer.deserialize(userSessionByte);  //加了热部署就会报错
                //因为程序Application创建的classLoader与springboot devtools热部署创建的restartClassLoader不一样
                return userSessionVo;
            }else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();

            return null;
        } finally {
            if(redisConnection != null){
                redisConnection.close();
            }
        }
    }

    @Override
    public void updatePwd(String userName, String password) throws Exception {
        TUser userCond = new TUser();
        userCond.setUserName(userName);
        TUser user = userDao.getUserByCond(userCond);
        if(user == null){
            throw new Exception("该用户不存在");
        }

        String passwordMd5 = Utils.getMd5Str(password);
        user.setPassword(passwordMd5);
        userDao.updatePwd(user);
    }
}
