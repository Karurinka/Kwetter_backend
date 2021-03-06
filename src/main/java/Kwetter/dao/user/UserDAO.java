package Kwetter.dao.user;

import Kwetter.dto.FollowerDTO;
import Kwetter.dto.UserDTO;
import Kwetter.model.Token;
import Kwetter.model.User;
import Kwetter.utility.HibernateSessionFactory;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Default
public class UserDAO implements IUserDAO {

    @Inject
    private HibernateSessionFactory sessionFactory;

    @Override
    public User getUserById(int userId) {
        Session session = sessionFactory.getCurrentSession();

        User user = session.get(User.class, userId);
        return user;
    }

    @Override
    public UserDTO getUserDTOById(int userId) {
        Session session = sessionFactory.getCurrentSession();

        User user = session.get(User.class, userId);

        return new UserDTO(user);
    }

    @Override
    public boolean follow(int followingId, int followerId) {
        User userFollowing = new User();
        User userFollower = new User();
        userFollowing = getUserById(followingId);
        userFollower = getUserById(followerId);
        userFollower.addFollower(userFollowing);

        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        session.merge(userFollower);
        session.getTransaction().commit();

        return true;
    }

    @Override
    public UserDTO editUser(int userId, UserDTO userJson) {
        User user = getUserById(userId);
        user.setName(userJson.getName());
        user.setImage(userJson.getImage());
        user.setUsername(userJson.getUsername());
        user.setBio(userJson.getBio());
        user.setLocation(userJson.getLocation());
        user.setWeb(userJson.getWeb());

        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        session.merge(user);
        session.getTransaction().commit();

        return new UserDTO(user);
    }

    @Override
    public boolean checkToken(String givenToken) {
        Session session = sessionFactory.getCurrentSession();

        Token token = (Token) session.createQuery("from Token where givenToken = :givenToken ")
                                     .setParameter("token", givenToken).getSingleResult();
        if(token != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getRoleId(int userId) {
        Session session = sessionFactory.getCurrentSession();

        User user = session.get(User.class, userId);

        return user.getRole().ordinal();
    }

    @Override
    public List<UserDTO> getFollowing(int userId) {
        User user = getUserById(userId);
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User u: user.getFollowing()) {
            userDTOS.add(new UserDTO(u));
        }

        return userDTOS;
    }

    @Override
    public List<UserDTO> getFollowers(int userId) {
        User user = getUserById(userId);
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User u: user.getFollowers()) {
            userDTOS.add(new UserDTO(u));
        }

        return userDTOS;
    }

    @Override
    public boolean unfollowUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = getUserById(userId);
        //TODO: unfollow that dude
        return true;
    }
}
