package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

import java.util.HashSet;
import java.util.Set;

public class DeveloperEvaluation extends EvaluationRule<Game, Attribute> {

    @Override
    public boolean isEqual(Game record1, Game record2, Attribute schemaElement) {
        Set<String> developers1 = extractDevelopers(record1);
        Set<String> developers2 = extractDevelopers(record2);

        return developers1.containsAll(developers2) && developers2.containsAll(developers1);
    }

    private Set<String> extractDevelopers(Game game) {
        Set<String> developers = new HashSet<>();

        if (game.getDeveloper() != null && !game.getDeveloper().isEmpty()) {
            developers.addAll(game.getDeveloper());
        }
        return developers;
    }

    @Override
    public boolean isEqual(Game record1, Game record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}
