package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.StockService;
import com.es.phoneshop.web.controller.dto.QuickQuantityForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller(value = "/quickCart")
public class QuickCartPageController {

    public static final String QUICK_QUANTITY_FORM = "quickQuantityForm";
    public static final String QUICK_CART_ADD = "quickCartAdd";
    public static final int NUMBER_OF_INPUT_FIELDS = 10;
    public static final String ERROR = "error";
    public static final String MODEL = "model";
    public static final String PHONE_IS_NOT_FOUND = "Phone is not found";
    public static final String QUANTITY = "quantity";
    public static final String OUT_OF_STOCK = "Out of stock";
    public static final String REDIRECT_CART = "redirect:/cart";
    public static final String QUICK_UPDATE_CART_REQUEST = "quickUpdateCartRequest";
    public static final String CART = "cart";

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private StockService stockService;

    @Resource
    private HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute(QUICK_QUANTITY_FORM, new QuickQuantityForm());
        model.addAttribute(CART, cartService.getCart(session));
//        model.addAttribute(QUICK_UPDATE_CART_REQUEST, new QuickUpdateCartRequest(NUMBER_OF_INPUT_FIELDS));
        return QUICK_CART_ADD;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToCart(@Valid QuickQuantityForm quickQuantityForm, BindingResult result) {
        if (result.hasErrors()) {
            return QUICK_CART_ADD;
        }
        try {
            Optional<Phone> phone = phoneDao.getPhoneByModel(quickQuantityForm.getModel());
        } catch (PhoneNotFindException e) {
            result.addError(new FieldError(ERROR, MODEL, PHONE_IS_NOT_FOUND));
            return QUICK_CART_ADD;
        }
        Long id = phoneDao.getPhoneByModel(quickQuantityForm.getModel()).get().getId();
        try {
            if (stockService.getAvailablePhoneStock(id) < quickQuantityForm.getQuantity()) {
                throw new OutOfStockException(id.toString());
            }
        } catch (OutOfStockException e) {
            result.addError(new FieldError(ERROR, QUANTITY, OUT_OF_STOCK));
            return QUICK_CART_ADD;
        }
        Long quantity = quickQuantityForm.getQuantity();
        Cart cart = cartService.getCart(session);
        cartService.addPhone(id, quantity.intValue(), cart);
        return REDIRECT_CART;
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/mult")
//    public String addToCartMult(@Valid QuickUpdateCartRequest quickUpdateCartRequest, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return QUICK_CART_ADD;
//        }
//        return REDIRECT_CART;
//    }
}
