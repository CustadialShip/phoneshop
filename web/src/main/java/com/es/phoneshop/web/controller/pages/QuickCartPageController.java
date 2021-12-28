package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.exception.PhoneNotFindException;
import com.es.core.model.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.StockService;
import com.es.phoneshop.web.controller.dto.QuickQuantityForm;
import com.es.phoneshop.web.controller.dto.QuickUpdateCartRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller(value = "/quickCart")
public class QuickCartPageController {

    public static final String QUICK_CART_ADD = "quickCartAdd";
    public static final String ERROR = "error";
    public static final String QUANTITY_ERROR = "quantityFormList[%d].quantity";
    public static final String MODEL_ERROR = "quantityFormList[%d].model";
    public static final String PHONE_IS_NOT_FOUND = "Phone is not found";
    public static final String OUT_OF_STOCK = "Out of stock";
    public static final String REDIRECT_CART = "redirect:/cart";
    public static final String CART = "cart";
    public static final String SUCCESS_MESSAGE_LIST = "successMessageList";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String THERE_WERE_SOME_PROBLEM = "There were some problem";
    public static final String SUCCESSFULLY_ADDED = " successfully added";

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private StockService stockService;

    @Resource
    private HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String show(@ModelAttribute QuickUpdateCartRequest quickUpdateCartRequest, Model model) {
        model.addAttribute(CART, cartService.getCart(session));
        return QUICK_CART_ADD;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToCart(@ModelAttribute @Valid QuickUpdateCartRequest quickUpdateCartRequest, BindingResult result,
                            Model model) {
        List<QuickQuantityForm> quantityFormList = quickUpdateCartRequest.getQuantityFormList();
        List<Integer> removeList = new ArrayList<>();
        List<String> successMessageList = new ArrayList<>();

        for (int i = 0; i < quantityFormList.size(); i++) {
            if (isValidQuickQuantityForm(quantityFormList, i) && !isHasErrorInForm(result, quantityFormList, i)) {
                cartService.addPhone(phoneDao.getPhoneByModel(quantityFormList.get(i).getModel()).get().getId(),
                        quantityFormList.get(i).getQuantity().intValue(),
                        cartService.getCart(session));
                successMessageList.add(quantityFormList.get(i).getModel() + SUCCESSFULLY_ADDED);
                removeList.add(i);
            }
        }
        removeList.forEach(id -> quantityFormList.set(id, new QuickQuantityForm()));
        if (result.hasErrors()) {
            model.addAttribute(SUCCESS_MESSAGE_LIST, successMessageList);
            model.addAttribute(ERROR_MESSAGE, THERE_WERE_SOME_PROBLEM);
            return QUICK_CART_ADD;
        }
        return REDIRECT_CART;
    }

    private boolean isHasErrorInForm(BindingResult result, List<QuickQuantityForm> quantityFormList, int i) {
        try {
            Optional<Phone> optionalPhone = phoneDao.getPhoneByModel(quantityFormList.get(i).getModel());
            Phone phone = optionalPhone.get();
            Long phoneId = phone.getId();
            if (stockService.getAvailablePhoneStock(phoneId) < quantityFormList.get(i).getQuantity()) {
                throw new OutOfStockException(phoneId.toString());
            }
        } catch (PhoneNotFindException e) {
            result.addError(new FieldError(ERROR, String.format(MODEL_ERROR, i), PHONE_IS_NOT_FOUND));
            return true;
        } catch (OutOfStockException e) {
            result.addError(new FieldError(ERROR, String.format(QUANTITY_ERROR, i), OUT_OF_STOCK));
            return true;
        }
        return false;
    }

    private boolean isValidQuickQuantityForm(List<QuickQuantityForm> quantityFormList, int i) {
        return quantityFormList.get(i).getQuantity() != null && quantityFormList.get(i).getModel() != null &&
                !quantityFormList.get(i).getModel().isEmpty();
    }
}
