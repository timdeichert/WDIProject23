package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class GameBlockingKeyByTitleGenerator extends RecordBlockingKeyGenerator<Game, Attribute> {

    private static final long serialVersionUID = 1L;


    /* (non-Javadoc)
     * @see de.uni_mannheim.informatik.wdi.matching.blocking.generators.BlockingKeyGenerator#generateBlockingKeys(de.uni_mannheim.informatik.wdi.model.Matchable, de.uni_mannheim.informatik.wdi.model.Result, de.uni_mannheim.informatik.wdi.processing.DatasetIterator)
     */
    @Override
    public void generateBlockingKeys(Game record, Processable<Correspondence<Attribute, Matchable>> correspondences,
                                     DataIterator<Pair<String, Game>> resultCollector) {

        String[] tokens  = record.getName().split(" ");

        String blockingKeyValue = "";

        for(int i = 0; i <= 2 && i < tokens.length; i++) {
            blockingKeyValue += tokens[i].substring(0, Math.min(2,tokens[i].length())).toUpperCase();
        }

        resultCollector.next(new Pair<>(blockingKeyValue, record));
    }
}
