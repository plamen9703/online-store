package com.porfolio.online_store.web.product;

import com.porfolio.online_store.dto.product.ProductCreationRequest;
import com.porfolio.online_store.dto.product.ProductDto;
import com.porfolio.online_store.dto.product.ProductUpdateRequest;
import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.service.product.ProductService;
import com.porfolio.online_store.service.user.UserSessionLoaderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.porfolio.online_store.constants.ApplicationConstants.PRODUCT_PAGE_DEFAULT_PAGE;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final UserSessionLoaderService userSessionLoaderService;

    private final ProductService productService;


    @GetMapping("/browse")
    public ModelAndView getProductsPage(
            @RequestParam(value = "page", required = false, defaultValue = PRODUCT_PAGE_DEFAULT_PAGE) Integer requestedPage,
            HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        if(requestedPage == 0){
            modelAndView.setViewName("redirect:/products/browse?page=1");
            return modelAndView;
        }
        UserDto user = userSessionLoaderService.loadUserFromSession(session);

        Page<ProductDto> products;

        int pageIndex = requestedPage - 1;
        if(user!=null){
            products  = productService.getProductsForUser(user.getId().toString(), pageIndex);
        }else {
            products = productService.getProductPage(pageIndex);
        }

        modelAndView.setViewName("products");
        modelAndView.addObject("user", user);
        modelAndView.addObject("products", products);
        modelAndView.addObject("currentPage", pageIndex);

        List<Integer> array = new ArrayList<>();
        IntStream.range(1, products.getTotalPages() + 1).forEach(array::add);
        modelAndView.addObject("pageNumbers", array);
        modelAndView.addObject("pageType","browse");

        return modelAndView;
    }

    @GetMapping("/own")
    public ModelAndView getUserProducts(
            @RequestParam(value = "page", required = false, defaultValue = PRODUCT_PAGE_DEFAULT_PAGE) Integer requestedPage,
            HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if (user == null){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        int pageIndex = requestedPage - 1;
        Page<ProductDto> products = productService.getUsersProducts(user.getId().toString(),pageIndex);

        modelAndView.setViewName("products");
        modelAndView.addObject("user", user);
        modelAndView.addObject("products", products);

        modelAndView.addObject("currentPage", pageIndex);

        List<Integer> array = new ArrayList<>();
        IntStream.range(1, products.getTotalPages() + 1).forEach(array::add);
        modelAndView.addObject("pageNumbers", array);
        modelAndView.addObject("pageType","own");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getProductDetailsPage(@PathVariable("id") String id, HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        ProductDto product = productService.getProductById(UUID.fromString(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product-details");
        modelAndView.addObject("user", user);
        modelAndView.addObject("product", product);
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView getProductCreatePage(HttpSession session){
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            session.invalidate();
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.setViewName("product-form-create");
        modelAndView.addObject("user", user);
        modelAndView.addObject("productData", ProductCreationRequest.builder().build());
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView getProductEditPage(@PathVariable("id") String productid, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        ProductDto product = productService.getProductById(UUID.fromString(productid));
        modelAndView.setViewName("product-form-edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("productData", product);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView crateProduct(
            @ModelAttribute("productData") @Valid ProductCreationRequest productCreationRequest,
            BindingResult bindingResult,
            HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            session.invalidate();
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("product-form-create");
            modelAndView.addObject("user", user);
            return modelAndView;
        }


        ProductDto product = productService.createProduct(user, productCreationRequest);

        modelAndView.setViewName("redirect:/products/own");

        return  modelAndView;
    }

    @PutMapping("/{id}")
    public ModelAndView editProduct(
            @PathVariable("id") String productId,
            @ModelAttribute("productData") @Valid ProductUpdateRequest productUpdateRequest,
            BindingResult bindingResult,
            HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);

        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        ProductDto product = productService.getProductById(UUID.fromString(productId));
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("product-form-edit");
            modelAndView.addObject("user", user);
            modelAndView.addObject("productData", product);
            return modelAndView;
        }
        productService.updateProduct(productId, productUpdateRequest, user);
        modelAndView.setViewName("redirect:/products/"+productId);

        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteProduct(
            @PathVariable("id") String productId,
            HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = userSessionLoaderService.loadUserFromSession(session);
        if(user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        productService.deleteProduct(productId,user);
        modelAndView.setViewName("redirect:/products/own");
        return modelAndView;
    }
}
