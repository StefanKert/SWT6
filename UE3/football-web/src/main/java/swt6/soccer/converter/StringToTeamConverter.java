package swt6.soccer.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import swt6.soccer.dao.TeamRepository;
import swt6.soccer.domain.Team;

@Component
public class StringToTeamConverter implements Converter<String, Team> {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team convert(String teamId) {
        Long id = new Long(teamId);
        return teamRepository.getOne(id);
    }

}
