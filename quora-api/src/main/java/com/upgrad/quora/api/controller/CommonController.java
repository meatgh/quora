package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.CommonBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {
    @Autowired
    private CommonBusinessService commonBusinessService;

    @RequestMapping(method = RequestMethod.GET,path = "/userprofile/{userId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> userProfile (@PathVariable("userId") String uuid, @RequestHeader(name = "authorization") String authorization) throws AuthorizationFailedException, UserNotFoundException {
        final UserEntity userDetails = commonBusinessService.getUserDetails(uuid, authorization);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().dob(userDetails.getDob()).firstName(userDetails.getFirstName()).lastName(userDetails.getLastName())
                .contactNumber(userDetails.getContactNumber()).emailAddress(userDetails.getEmail()).country(userDetails.getCountry())
                .userName(userDetails.getUserName()).aboutMe(userDetails.getAboutMe());

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }


}

