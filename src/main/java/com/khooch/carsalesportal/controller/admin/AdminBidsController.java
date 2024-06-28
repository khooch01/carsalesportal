package com.khooch.carsalesportal.controller.admin;

import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.service.BidService;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminBidsController {

    private final BidService bidService;

    public AdminBidsController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/admin/bids")
    public String listBids(Model model) {
        List<Bid> bids = bidService.getAllBids();
        model.addAttribute("bids", bids);
        return "admin/manageBids";
    }

    @GetMapping("/admin/bids/approve/{id}")
    public String approveBid(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bidService.approveBid(id);
            redirectAttributes.addFlashAttribute("successMessage", "Bid approved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to approve bid: " + e.getMessage());
        }
        return "redirect:/admin/bids";
    }

    @GetMapping("/admin/bids/deny/{id}")
    public String denyBid(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bidService.denyBid(id);
            redirectAttributes.addFlashAttribute("successMessage", "Bid denied successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to deny bid: " + e.getMessage());
        }
        return "redirect:/admin/bids";
    }

    @PostMapping("/admin/bids/approve/{id}")
    public String approveBid(@PathVariable Long id) {
        bidService.approveBid(id);
        return "redirect:/admin/bids";
    }

    @PostMapping("/admin/bids/reject/{id}")
    public String rejectBid(@PathVariable Long id) {
        bidService.rejectBid(id);
        return "redirect:/admin/bids";
    }
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public String handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to process bid: " + ex.getMessage());
        return "redirect:/admin/bids";
    }
}
