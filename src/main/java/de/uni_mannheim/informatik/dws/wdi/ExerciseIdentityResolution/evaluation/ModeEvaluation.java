package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

import java.util.HashSet;
import java.util.Set;

public class ModeEvaluation extends EvaluationRule<Game, Attribute> {

    @Override
    public boolean isEqual(Game record1, Game record2, Attribute schemaElement) {
        Set<String> mode1 = extractModes(record1);
        Set<String> mode2 = extractModes(record2);

        return mode1.containsAll(mode2) && mode2.containsAll(mode1);
    }

    private Set<String> extractModes(Game game) {
        Set<String> modes = new HashSet<>();

        if (game.getMode() != null && !game.getMode().isEmpty()) {
            modes.addAll(game.getMode());
        }
        return modes;
    }

    @Override
    public boolean isEqual(Game record1, Game record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}
