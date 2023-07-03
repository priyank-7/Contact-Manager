package com.smart.smartcontectmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.smartcontectmanager.Entity.User;
import com.smart.smartcontectmanager.Helper.Message;
import com.smart.smartcontectmanager.Reposetory.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class homeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    // Handler for home page
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "This is home Page");
        return "home";
    }

    // Handler for about page
    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }
    
    // Handler for signup page
    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User());
        return "signup";
    }

    // Handler for Registring user
    @PostMapping("/do_register")
    public String RegisterUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) throws Exception {       
        
       
        try {
            if(!agreement){
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");
            }

            if(result.hasErrors()){
                // System.out.println(result);
                model.addAttribute("user", user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            this.userRepository.save(user);
            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Sucessfully Registered", "alert-success"));
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something Went wrong !!" + e.getMessage(), "alert-danger"));
            return "signup";
        }        
    }

    // Handler for custom login
    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title", "Login");
        return "login";
    }
}
