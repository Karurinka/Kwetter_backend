package Kwetter.service;

import Kwetter.dao.user.IUserDAO;
import Kwetter.dto.UserDTO;
import Kwetter.model.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("UserService")
@Default
public class UserService {

    @Inject
    private IUserDAO userDAO;

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    UserDTO getUserDTOById(int userId) {
        return userDAO.getUserDTOById(userId);
    }

    public void follow(int followingId, int followerId) {
        userDAO.follow(followingId, followerId);
    }

    public UserDTO editUser(int userId, UserDTO userJson) {
        return userDAO.editUser(userId, userJson);
    }
}
