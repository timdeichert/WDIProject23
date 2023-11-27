// package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers;

// import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
// import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
// import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.numeric.Average;
// import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
// import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
// import de.uni_mannheim.informatik.dws.winter.model.Matchable;
// import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
// import de.uni_mannheim.informatik.dws.winter.processing.Processable;

// /**
//  * {@link AttributeValueFuser} for the EU sales of {@link Game}s, calculating the average.
//  * 
//  * @author Your Name
//  */
// public class EuSalesFuserAverage extends AttributeValueFuser<Float, Game, Game> {

//     public EuSalesFuserAverage() {
//         super(new Average<>());
//     }

//     @Override
//     public boolean hasValue(Game record, Correspondence<Game, Matchable> correspondence) {
//         return record.getEU_Sales() != null;
//     }

//     @Override
//     public Float getValue(Game record, Correspondence<Game, Matchable> correspondence) {
//         return record.getEU_Sales();
//     }

//     @Override
//     public void fuse(RecordGroup<Game, Game> group, Game fusedRecord,
//                      Processable<Correspondence<Game, Matchable>> schemaCorrespondences, Game schemaElement) {
//         FusedValue<Float, Game, Game> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
//         fusedRecord.setEU_Sales(fused.getValue());
//         fusedRecord.setAttributeProvenance(Game.EU_SALES, fused.getOriginalIds());
//     }
// }
