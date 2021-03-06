package swt6.soccer.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import swt6.soccer.dao.BetRepository;
import swt6.soccer.dao.GameRepository;
import swt6.soccer.dao.UserRepository;
import swt6.soccer.domain.Bet;
import swt6.soccer.logic.BetSystemService;

@Controller
public class BetController {

    @Autowired
    private BetSystemService betSystemService;
    
    @Autowired
    private BetRepository betRepository;
    
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
    
    @RequestMapping(value = "/bets")
    public String listBets(Model model) {
		model.addAttribute("bets", betRepository.findAll());
		return "bets/overview";
    }

    @RequestMapping(value = "/bets/{gameId}/create", method = RequestMethod.GET)
    public String initNew(@PathVariable("gameId") Long gameId, Model model){
		Bet bet = new Bet();
		model.addAttribute("bet", bet);
		model.addAttribute("game", gameRepository.findOne(gameId));
		model.addAttribute("users", userRepository.findAll());
        return "bets/setBet";
    }
    
    @RequestMapping(value = "/bets/{gameId}/create", method = RequestMethod.POST)
    public String create(@PathVariable("gameId") Long gameId, @Valid @ModelAttribute("bet") Bet bet, BindingResult result, Model model){
		if (result.hasErrors()) {
			model.addAttribute("bet", bet);
			model.addAttribute("game", gameRepository.findOne(gameId));
			model.addAttribute("users", userRepository.findAll());
			model.addAttribute("errors" ,result.getAllErrors());
			return "bets/setBet";
		}
		betSystemService.saveBetWithGame(bet, gameId);
		return "redirect:/games";
    }
}
