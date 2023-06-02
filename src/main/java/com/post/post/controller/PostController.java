package com.post.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.post.payload.PostDto;
import com.post.post.service.PostService;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/")
public class PostController {

    @Autowired
    private PostService postService;

    
    // API JSON BODY 
    // {
    //     "transactionId": "123456789",
    //     "amount": 149.99,
    //     "currency": "USD",
    //     "customer": {
    //       "id": "987654321",
    //       "name": "Jane Smith",
    //       "email": "janesmith@example.com"
    //     },
    //     "paymentMethod": {
    //       "customerid":"",
    //       "type": "creditCard",
    //       "cardNumber": "1234 5678 9012 3456",
    //       "expirationMonth": "05",
    //       "expirationYear": "2024",
    //       "cvv": "123",
    //       "billindaddressid":1,
    //       "billingAddress": {
    //         "billindaddressid":1,
    //         "street": "456 Elm St",
    //         "city": "Los Angeles",
    //         "state": "CA",
    //         "zip": "90001"
    //       }
    //     },
    //     "orders": {
    //       "orderid": "1",
    //       "customerid": "987654321",
    //       "orderdate": "12-01-2023"
    //     },
    //     "orderdetails": [
    //       {
    //     "orderdetailsid": "1",
    //     "orderid": "1",
    //     "productid": "1",
    //     "price": 49.99,
    //     "quantity": 2
    //   },
    //   {
    //     "orderdetailsid": "",
    //     "orderid": "1",
    //     "productid": "2",
    //     "price": 25,
    //     "quantity": 1
    //   },
    //   {
    //     "orderdetailsid": "3",
    //     "orderid": "",
    //     "productid": "3",
    //     "price": 55,
    //     "quantity": 3
    //   }
      
    //   ]
    //   }
    // JSON Request 
    @PostMapping(value = "/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processJsonData(@RequestBody String jsonRequest) {
        try {
            JsonNode rootNode = new ObjectMapper().readTree(jsonRequest);
            String cardNumber = rootNode.path("paymentMethod").path("cardNumber").asText();
            String city = rootNode.path("paymentMethod").path("billingAddress").path("city").asText();
            // So we can get any value 
            return ResponseEntity.ok("Card Number: " + cardNumber + " City: "+ city);

        } catch (Exception e) {
            // Handle any exceptions that occur during JSON parsing
            return ResponseEntity.badRequest().body("Error processing JSON data");
        }
    }


    // create blog post rest api
    @PostMapping("posts")
    public PostDto createPost(@Valid @RequestBody PostDto postDto){
        return postService.createPost(postDto, HttpStatus.CREATED);
    }

    // get all posts rest api
    @GetMapping("posts")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    // get post by id
    @GetMapping(value = "posts/{postId}")
    public PostDto getPostById(@PathVariable(name = "postId") Long postId){
        return postService.getPostById(postId);
    }

     // update post by id rest api
     @PutMapping("posts/{postId}")
     public PostDto updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "postId") Long postId){
 
        PostDto postResponse = postService.updatePost(postDto, postId);
 
        return postResponse;
     }

    // delete post rest api
    @DeleteMapping("posts/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long postId){

        postService.deletePostById(postId);

        return "Post entity deleted successfully.";
    }

}
