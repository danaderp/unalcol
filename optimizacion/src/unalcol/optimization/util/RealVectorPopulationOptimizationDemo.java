/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.optimization.iterative.IterativePopulationOptimizer;
import unalcol.instance.InstanceProvider;
import unalcol.optimization.*;
import unalcol.optimization.operators.*;
import unalcol.optimization.transformation.*;
import unalcol.algorithm.iterative.*;
import unalcol.instance.InstanceService;
import unalcol.math.logic.*;
import unalcol.reflect.service.*;
import unalcol.reflect.util.*;
import unalcol.io.*;
import unalcol.optimization.generation.Repair;
import unalcol.optimization.operators.real.*;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionInstance;
import unalcol.optimization.testbed.real.*;
import unalcol.random.real.*;
import unalcol.types.real.array.DoubleArraySimpleWriteService;
import unalcol.tracer.*;
import unalcol.types.collection.vector.Vector;
import unalcol.types.real.array.DoubleArrayInit;
import unalcol.types.real.array.DoubleArrayInstance;

/**
 *
 * @author jgomez
 */
public class RealVectorPopulationOptimizationDemo {
    public static void main( String[] args ){
        // Reflection
        ServiceProvider provider = ReflectUtil.getProvider("services/");
        DoubleArraySimpleWriteService key = new DoubleArraySimpleWriteService(',');
        provider.register(key);
        provider.setDefault_service(WriteService.class,double[].class,key);

        // Search Space 
        int DIMENSION = 10;
        double[] min = DoubleArrayInit.create(DIMENSION, -512.0);
        double[] max = DoubleArrayInit.create(DIMENSION, 512.0);
        InstanceService ikey = new DoubleArrayInstance(min, max);
        double[] t = new double[DIMENSION];
        t = (double[])ikey.get(t);
        for( int i=0; i<DIMENSION; i++ ){
          System.out.println(min[i]);
        }
        provider.register(ikey);
        provider.setDefault_service(InstanceService.class,double[].class,ikey);
        Repair<double[]> rkey = new RealVectorScaleRepair(min, max); 
        provider.register(rkey);
        provider.setDefault_service(Repair.class,double[].class,rkey);

        // Solution Space
        double[] x = new double[DIMENSION];
        Solution<double[]> solution = new Solution<>(x);
        SolutionInstance skey = new SolutionInstance();
        provider.register(skey);
        provider.setDefault_service(InstanceService.class,Solution.class,skey);

        // Initial population
        int POPSIZE = 100;
        Vector<Solution<double[]>> pop = InstanceProvider.get(solution, POPSIZE);

        // Function being optimized
        OptimizationFunction function = new Schwefel(); 
        
        // Evaluating the fitness of the initial population
        Solution.evaluate((Vector)pop,function);

        double sigma = 0.2;
        GaussianMutation mutation = new GaussianMutation(DoubleArrayInit.create(DIMENSION, sigma));
 /*       
        ArityOne mutation = new EllipticMutation(DIMENSION, sigma,
//                new StandardGaussianGenerator());
                new SimplestSymmetricPowerLawGenerator());
*/
        // Hill Climbing
//        Transformation transformation = new HillClimbing(mutation);
        Transformation transformation = new ESOneFifth(mutation);

        // Evolution generations
        int MAXITER = 10;
        Predicate condition = new ForLoopCondition(MAXITER);

        // Evolutionary algorithm (is a population optimizer)
        PopulationOptimizer optimizer = new IterativePopulationOptimizer(condition,
                transformation, pop);

        // A console set tracer
        Tracer tracer = new ConsoleTracer(optimizer);
        // Adding the tracer collection to the given population optimizer (evolutionary algorithm)
        provider.register(tracer);
        //tracer = new FileTracer(optimizer, "hc.txt", true);
        //provider.register(tracer);

        // running the population optimizer (the evolutionary algorithm)
        optimizer.apply(function);

        tracer.close();
 
    }    
}
