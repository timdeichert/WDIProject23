/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link EvaluationRule} for the actors of {@link Game}s. The rule simply
 * compares the full set of actors of two {@link Game}s and returns true, in
 * case they are identical.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PublisherEvaluationRule extends EvaluationRule<Game, Attribute> {

    @Override
    public boolean isEqual(Game record1, Game record2, Attribute schemaElement) {
        Set<String> publisher1 = extractPublishers(record1);
        Set<String> publisher2 = extractPublishers(record2);

        return publisher1.containsAll(publisher2) && publisher2.containsAll(publisher1);
    }

    private Set<String> extractPublishers(Game game) {
        Set<String> publishers = new HashSet<>();

        if (game.getPublisher() != null && !game.getPublisher().isEmpty()) {
            publishers.addAll(game.getPublisher());
        }
        return publishers;
    }

    @Override
    public boolean isEqual(Game record1, Game record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute) null);
    }
}
