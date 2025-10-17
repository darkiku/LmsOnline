//package com.example.LmsProject.Controller;
//
//import com.example.LmsProject.Facade.StudentPortalFacade;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@AllArgsConstructor
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api/demo")
//public class DemoController {
//    private StudentPortalFacade  studentPortalFacade;
//    @GetMapping("/patterns")
//    public ResponseEntity<?> demonstratePatterns() {
//        studentPortalFacade.demonstratePatterns();
//        return ResponseEntity.ok("Design patterns demonstration completed! Check server console for output.");
//}
//}
