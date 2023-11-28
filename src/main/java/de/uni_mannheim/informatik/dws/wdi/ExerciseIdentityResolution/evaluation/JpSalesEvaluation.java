package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class JpSalesEvaluation extends EvaluationRule<Game, Attribute> {

    @Override
    public boolean isEqual(Game record1, Game record2, Attribute schemaElement) {
        if(record1.getJP_Sales()==null && record2.getJP_Sales()==null)
            return true;
        else if(record1.getJP_Sales()==null ^ record2.getJP_Sales()==null)
            return false;
        else
            return record1.getJP_Sales().equals(record2.getJP_Sales());
    }

    @Override
    public boolean isEqual(Game record1, Game record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}
