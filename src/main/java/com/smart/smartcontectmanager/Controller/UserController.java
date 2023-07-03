package com.smart.smartcontectmanager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.smart.smartcontectmanager.Entity.Contect;
import com.smart.smartcontectmanager.Entity.User;
import com.smart.smartcontectmanager.Helper.Message;
import com.smart.smartcontectmanager.Helper.SessionHelper;
import com.smart.smartcontectmanager.Reposetory.ContactRepository;
import com.smart.smartcontectmanager.Reposetory.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    // Method for common data
    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);
    }

    // DASHBOARD HOME
    @GetMapping("/index")
    public String dashbord(Model model, Principal principal) {
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        model.addAttribute("title", "Dashbord");
        model.addAttribute("user", user);
        return "normal/user_dashbord";
    }


    // HANDLLER FOR ADDING CONTACT
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contect());
        return "normal/add_contact_form";
    }

    // Handller For processing adding contact
    @PostMapping("/process-contact")
    public String processContact(
        @ModelAttribute Contect contact, 
        @RequestParam("profileImage") MultipartFile file, 
        Principal principal,
        Model model,
        HttpSession session)
    {

        try{
    
            String name = principal.getName();
            // Getting User from databse
            User user = this.userRepository.getUserByUserName(name);

            // processing and uploading file
            if(file.isEmpty()){
                // if the file is empty then try our message
                System.out.println("File is empty");
                contact.setImage("contact.png");
            }
            else{
                // upload file to the folder and update the name to contact

                // Setting the file name to the contact object
                contact.setImage(file.getOriginalFilename());

                // Uploading file to the folder
                File saveFile = new ClassPathResource("static/img").getFile();

                // Copy the sata of the file and save to the defined path above with defined name above
                Files.copy(file.getInputStream(), Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }

            // Setting User to contact
            contact.setUser(user);
            // Setting Contact to user object
            user.getContects().add(contact);
            // Adding/Updating A user to the database with contact Details
            this.userRepository.save(user);
            // System.out.println(contact);
            // System.out.println("Successfull");

            // message Sucess
            session.setAttribute("status", new Message("Contact Added Successfully", "success"));
            model.addAttribute("status_remover", new SessionHelper());

        }
        catch(Exception e){
            System.out.println("Error"+e.getMessage());
            e.printStackTrace();

            // Error Message
            session.setAttribute("status", new Message("Something Went Wrong", "danger"));
            model.addAttribute("status_remover", new SessionHelper());

        }
        
        return "normal/add_contact_form";
    }

    // HANDLER FOR SHOWING CONTACTS
    // pre page = 5[n]
    // Current Page = 0[page]
    @GetMapping("/contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal){
        model.addAttribute("title", "Contacts");

        Pageable pageable = PageRequest.of(page, 5);
        Page<Contect> contacts = this.contactRepository.findContactByUser(this.userRepository.getUserByUserName(principal.getName()).getId(), pageable);
        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages",contacts.getTotalPages());

        return "normal/show_contacts";
    }

    // Handler For Contact Details

    @GetMapping("/contact/{cId}")
    public String ContactDetail(Model model, @PathVariable("cId") Integer cId, Principal principal){
        System.out.println("CID"+cId);

        Optional<Contect> contact_detail = this.contactRepository.findById(cId);
        Contect contect = contact_detail.get();
        // System.out.println(contect.getUser());

        if(contect.getUser().getId() != this.userRepository.getUserByUserName(principal.getName()).getId()){
            // model.addAttribute("ck", false);
            model.addAttribute("title", "Not Found");
        }
        else{
            model.addAttribute("contact", contect);
            model.addAttribute("title", contect.getName());
        }

        return "normal/contact_detail";
    }
}
