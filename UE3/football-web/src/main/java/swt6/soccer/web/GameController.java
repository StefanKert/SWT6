package swt6.soccer.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import swt6.soccer.dao.GameRepository;
import swt6.soccer.dao.TeamRepository;
import swt6.soccer.domain.Game;
import swt6.soccer.logic.BetSystemService;

@Controller
public class GameController {

	@Autowired
	private BetSystemService soccerService;
	   
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamRepository teamRepository;
    
	private final Logger logger = LoggerFactory.getLogger(GameController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping(value = "/games/current")
	public String listCurrentGames(Model model) {
		model.addAttribute("games", soccerService.getAllNotFinishedGamesSortedByDate());
		return "games/current";
	}

	@RequestMapping(value = "/games")
	public String listGames(Model model) {
		model.addAttribute("games", soccerService.getAllGamesSortedByDate());
		return "games/overview";
	}

	@RequestMapping(value = "/games/{gameId}", method = RequestMethod.GET)
	public String details(@PathVariable("gameId") Long gameId, Model model) {
		model.addAttribute("teams", teamRepository.findAll());
		model.addAttribute("game", gameRepository.getOne(gameId));
		return "games/details";
	}

	@RequestMapping(value = "/games/create", method = RequestMethod.GET)
	public String detailsCreate(Model model) {
		model.addAttribute("teams", teamRepository.findAll());
		model.addAttribute("game", new Game());
		return "games/details";
	}

	@RequestMapping(value = "/games/{gameId}", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("game") Game game, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("teams", teamRepository.findAll());
			model.addAttribute("game", game);
			logger.info("" + result.getAllErrors().size());
			model.addAttribute("errors" ,result.getAllErrors());
			return "games/details";
		}

		gameRepository.saveAndFlush(game);
		return "redirect:/games";
	}

	@RequestMapping(value = "/games/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("game") Game game, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("teams",  teamRepository.findAll());
			model.addAttribute("game", game);
			model.addAttribute("errors" ,result.getAllErrors());
			return "games/details";
		}

		gameRepository.saveAndFlush(game);
		return "redirect:/games";
	}
}
