package com.example.springmvcproject.controller;

import com.example.springmvcproject.model.Product;
import com.example.springmvcproject.repositories.ProductRepositories;
import com.example.springmvcproject.serviceImpl.OrderServiceImpl;
import com.example.springmvcproject.serviceImpl.ProductServiceImpl;
import com.example.springmvcproject.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductServiceImpl productService;
    private UsersServiceImpl usersService;
    private OrderServiceImpl orderService;
    @Autowired
    public ProductController(ProductServiceImpl productService, UsersServiceImpl usersService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.usersService = usersService;
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ModelAndView findAllProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product> productList = productService.findAllProducts.get();
        return new ModelAndView("dashboard")
                .addObject("products", productList)
                .addObject("cartItems", "Cart Items: "+session.getAttribute("cartItems"));
    }

    @GetMapping("/admin/all")
    public ModelAndView findAllAdminProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product> productList = productService.findAllProducts.get();
        return new ModelAndView("admin-dashboard")
                .addObject("products", productList)
                .addObject("cartItems", "Cart Items: "+session.getAttribute("cartItems"));
    }

    @GetMapping("/add-cart")
    public String addToCart(@RequestParam(name = "cart") Long id, HttpServletRequest request, Model model){
        productService.addProductToCart(id, request);
        return "redirect:/products/all";
    }
    @GetMapping("/add-product")
    public String addNewProductToStore(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "price") BigDecimal price,
            @RequestParam(name = "productName") String productName,
            @RequestParam(name = "quantity") Long quantity) {
        productService.addNewProduct(category, price, productName, quantity);
        return "redirect:/products/admin/all";
    }



    @GetMapping("/payment")
    public String checkOut(HttpSession session, Model model){
        productService.checkOutCart(session, model);
        model.addAttribute("paid", "");
        return "checkout";
    }

    @GetMapping("/pay")
    public String orderPayment(HttpSession session, Model model){
        return orderService.makePayment(session, model);
    }

}
