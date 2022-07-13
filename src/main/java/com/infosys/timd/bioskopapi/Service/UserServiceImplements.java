package com.infosys.timd.bioskopapi.Service;

import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;;
import com.infosys.timd.bioskopapi.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplements implements UserService{
    private final UserRepository userRepository;

    /***
     * Get All User
     * @return
     */
    public List<User> getAll(){
        List<User> user = userRepository.findAll();
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :");
        }
        return this.userRepository.findAll();

    }

    /***
     * Get User By ID
     * @param users_Id
     * @return
     */
    public Optional<User> getUserById(Long users_Id){
        Optional<User> optionalUser = userRepository.findById(users_Id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        return this.userRepository.findById(users_Id);
    }

    /***
     * Create User
     * @param user
     * @return
     */
    public User createUser(User user){

        return this.userRepository.save(user);
    }

    /***
     * Delete User
     * @param users_Id
     */
    @Override
    public void deleteUserById(Long users_Id) {
        Optional<User> optionalUser = userRepository.findById(users_Id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        User user = userRepository.getReferenceById(users_Id);
        this.userRepository.delete(user);
    }

    /***
     * Update User
     * @param user
     * @return
     * @throws Exception
     */
    public User updateUser(User user) throws Exception{
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + user.getUserId());
        }
        return this.userRepository.save(user);
    }

    @Override
    public User getReferenceById(Long users_Id) {
        Optional <User> optional = userRepository.findById(users_Id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new ResourceNotFoundException("User Not Found!" + users_Id);
        }
        return user;

    }

    @Override
    public User getUserId(Long users_Id) {
        Optional <User> optional = userRepository.findById(users_Id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new ResourceNotFoundException("User Not Found!" + users_Id);
        }
        return user;
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        return this.userRepository.findAll(pageable);
    }

//    @Override
//    public List<User> getUserByNameLike(String name) {
//        List<User> getUserByNameLike = this.userRepository.getUserNameLike(name);
//        if (getUserByNameLike.isEmpty()){
//            throw new ResourceNotFoundException("Username " + name + " is not exist!");
//        }
//        return this.userRepository.getUserNameLike(name);
//    }

    @Override
    public List<User> getByKeyword(String keyword) {
        List<User> getUserByName = this.userRepository.findByKeyword(keyword);
        if (getUserByName.isEmpty()){
            throw new ResourceNotFoundException("Username " + keyword + " is not exist!");
        }
        return this.userRepository.findByKeyword(keyword);
    }

}