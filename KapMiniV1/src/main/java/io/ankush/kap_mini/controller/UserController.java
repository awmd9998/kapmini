package io.ankush.kap_mini.controller;

import io.ankush.kap_mini.domain.SubDomain;
import io.ankush.kap_mini.model.Isactive;
import io.ankush.kap_mini.model.Status;
import io.ankush.kap_mini.model.UserDTO;
import io.ankush.kap_mini.repos.SubDomainRepository;
import io.ankush.kap_mini.service.UserService;
import io.ankush.kap_mini.util.CustomCollectors;
import io.ankush.kap_mini.util.ReferencedWarning;
import io.ankush.kap_mini.util.WebUtils;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SubDomainRepository subDomainRepository;

    public UserController(final UserService userService,
            final SubDomainRepository subDomainRepository) {
        this.userService = userService;
        this.subDomainRepository = subDomainRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("isActiveValues", Isactive.values());
        model.addAttribute("statusValues", Status.values());
        model.addAttribute("subdomainIDValues", subDomainRepository.findAll(Sort.by("subdomain"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SubDomain::getSubdomain, SubDomain::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserDTO userDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users";
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable(name = "userId") final UUID userId, final Model model) {
        model.addAttribute("user", userService.get(userId));
        return "user/edit";
    }

    @PostMapping("/edit/{userId}")
    public String edit(@PathVariable(name = "userId") final UUID userId,
            @ModelAttribute("user") @Valid final UserDTO userDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(userId, userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/users";
    }

    @PostMapping("/delete/{userId}")
    public String delete(@PathVariable(name = "userId") final UUID userId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = userService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            userService.delete(userId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        }
        return "redirect:/users";
    }

}
