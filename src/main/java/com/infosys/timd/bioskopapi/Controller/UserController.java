package com.infosys.timd.bioskopapi.Controller;

import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import com.infosys.timd.bioskopapi.Repository.*;

import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Edited By Rendra
 */
@RestController
@RequestMapping("/teamD/v1/")
@AllArgsConstructor
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private final UserServiceImplements userServiceImplements;
    private final UserRepository userRepository;

    /***
     * Get All Users, Logger And Response DONE
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity <Object> getAllUser() {
        try {
            List <User> result = userServiceImplements.getAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Users     ====================");
            for(User userData : result){
                Map<String, Object> user = new HashMap<>();

                logger.info("-------------------------");
                logger.info("ID       : " + userData.getUserId());
                logger.info("Username : " + userData.getUsername());
                logger.info("Email    : " + userData.getEmailId());
                logger.info("Password : " + userData.getPassword());

                user.put("ID            ", userData.getUserId());
                user.put("Username      ", userData.getUsername());
                user.put("Email         ", userData.getEmailId());
                user.put("Password      ", userData.getPassword());
                maps.add(user);
            }
            logger.info("==================== Logger End Get All Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Get All User!", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value"));
            logger.info("==================== Logger End Get All Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table Has No Value!");
        }
    }

    /***
     * Get User By Id, Logger and Response DONE
     * @param users_Id
     * @return
     */
    @GetMapping("/users/{users_Id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long users_Id) {
        try {
//            User userResult = userRepository.getReferenceById(users_Id);
            User userResult = userServiceImplements.getUserById(users_Id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with user_Id :" + users_Id));
            Map<String, Object> user = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();

            logger.info("==================== Logger Start Find By ID Users ====================");
            logger.info("ID       : " + userResult.getUserId());
            logger.info("Username : " + userResult.getUsername());
            logger.info("Email    : " + userResult.getEmailId());
            logger.info("Password : " + userResult.getPassword());

            user.put("ID             ", userResult.getUserId());
            user.put("Username       ", userResult.getUsername());
            user.put("Email          ", userResult.getEmailId());
            user.put("Password       ", userResult.getPassword());
            maps.add(user);

            logger.info("==================== Logger End Find By ID Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Get User By ID!", HttpStatus.OK, maps);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get By ID Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!"));
            logger.info("==================== Logger End Get By ID Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }

    }

    /***
     * Create User, Logger and Response DONE
     * @param user
     * @return
     */
    @PostMapping("/users")
    public ResponseEntity <Object> createUser(@RequestBody User user) {

        try {
            userServiceImplements.createUser(user);
            User userResult = userServiceImplements.createUser(user);
            Map<String, Object> userMap = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();

            logger.info("==================== Logger Start Create Users ====================");
            logger.info("User Successfully Created !");
            logger.info("ID       : " + userResult.getUserId());
            logger.info("Username : " + userResult.getUsername());
            logger.info("Email    : " + userResult.getEmailId());
            logger.info("Password : " + userResult.getPassword());

            userMap.put("ID             ", userResult.getUserId());
            userMap.put("Username       ", userResult.getUsername());
            userMap.put("Email          ", userResult.getEmailId());
            userMap.put("Password       ", userResult.getPassword());
            maps.add(userMap);
            logger.info("==================== Logger End Create Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Created User!", HttpStatus.CREATED, maps);
        } catch (Exception e) {
            logger.info("==================== Logger Start Create Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"User Already Exist!"));
            logger.info("==================== Logger End Create Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "User Already Exist!");
        }

    }

    /***
     * Update User, Logger and Response DONE
     * @param users_Id
     * @param userDetails
     * @return
     */
    @PutMapping("/users/{users_Id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long users_Id, @RequestBody User userDetails){
        try {
            User user = userServiceImplements.getUserById(users_Id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with user_Id :" + users_Id));

            user.setUsername(userDetails.getUsername());
            user.setEmailId(userDetails.getEmailId());
            user.setPassword(userDetails.getPassword());
            User updatedUser = userRepository.save(user);

            logger.info("==================== Logger Start Update Users ====================");
            logger.info("User Data Successfully Updated !");
            logger.info("ID       : " + user.getUserId());
            logger.info("Username : " + user.getUsername());
            logger.info("Email    : " + user.getEmailId());
            logger.info("Password : " + user.getPassword());
            logger.info("==================== Logger End Update Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Updated User!",HttpStatus.OK, user);
        }catch(Exception e){
            logger.info("==================== Logger Start Update Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!"));
            logger.info("==================== Logger End Update Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!");
        }

    }

    /***
     * Delete User,Logger and Response DONE
     * @param users_Id
     * @return
     */
    @DeleteMapping("/users/{users_Id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long users_Id){
        try {
            userServiceImplements.deleteUserById(users_Id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info("==================== Logger Start Delete Users ====================");
            logger.info("User Data Successfully Deleted! :" + response.put("deleted", Boolean.TRUE));
            logger.info("==================== Logger End Delete Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Delete User! ", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e){
            logger.info("==================== Logger Start Delete Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!"));
            logger.info("==================== Logger End Delete Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }
}