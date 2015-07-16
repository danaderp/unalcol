/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.generation.RandomCandidatesGeneration;
import unalcol.optimization.operators.real.GaussianMutation;
import unalcol.optimization.replacement.OneToOneSteadyState;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.transformation.Transformation;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Jonatan
 */
public class ESOneFifth extends Transformation<double[]>{
    protected static final int G = 20; 
    protected static final double a = 0.9; 
    protected int k=0;
    protected int[] counter = null;
    protected double[][] sigma = null;
    protected GaussianMutation mutation;
    protected ESOneFifthRandomCandidatesGeneration gen;
    public ESOneFifth( GaussianMutation mutation ){
        super( new ESOneFifthRandomCandidatesGeneration(mutation), new OneToOneSteadyState());
        this.mutation = mutation;
        this.gen = (ESOneFifthRandomCandidatesGeneration)strategy;
    }
    
    
    /**
     * Transforms the given population to another population according to its rules.
     * @param population The population to be transformed
     * @return Offspring population
     */
    @Override
    public Vector<Solution<double[]>> apply( Vector<Solution<double[]>> population, OptimizationFunction<double[]> f ){
        if( counter == null ){
            double[] s = mutation.getSigma();
            counter = new int[population.size()];
            sigma = new double[counter.length][];
            for( int i=0; i<counter.length; i++){
                sigma[i] = s.clone();
            }
        }
        k++;
        if(k==G){
            // The 1/5 rule
            double p;
            for( int i=0; i<counter.length; i++){
                p = (double)counter[i]/(double)G;
                if(p>0.2){
                    for( int j=0; j<sigma[i].length; j++ ){
                        sigma[i][j] /= a;
                    }
                }else{
                    if( p<0.2 ){
                        for( int j=0; j<sigma[i].length; j++ ){
                            sigma[i][j] *= a;
                        }
                    }
                }
                counter[i] = 0;
            }
            k=0;
        }
        gen.setSigma(sigma);
        Vector<Solution<double[]>> offspring = strategy.apply(population);
        Solution.evaluate((Vector)offspring,f);
        if( f.isNonStationary() ){
            Solution.evaluate((Vector)population,f);
        }
        for( int i=0; i<counter.length; i++){
            if(offspring.get(i).value() < population.get(i).value() ){
                counter[i]++;
            }
        }
        return replacement.apply(population, offspring);
    }

    
}
