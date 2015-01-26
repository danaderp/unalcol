/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.instance.InstanceProvider;
import unalcol.optimization.*;
import unalcol.optimization.operators.*;
import unalcol.algorithm.iterative.*;
import unalcol.instance.InstanceService;
import unalcol.math.logic.*;
import unalcol.reflect.service.*;
import unalcol.reflect.util.*;
import unalcol.io.*;
import unalcol.optimization.generation.Repair;
import unalcol.optimization.iterative.IterativeOptimizer;
import unalcol.optimization.operators.real.*;
import unalcol.optimization.random.RandomOptimizer;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionInstance;
import unalcol.optimization.solution.SolutionTransformation;
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
public class RealVectorOptimizationDemo {
    public static void main( String[] args ){
        // Reflection
        ServiceProvider provider = ReflectUtil.getProvider("services/");
        DoubleArraySimpleWriteService key = new DoubleArraySimpleWriteService(',');
        provider.register(key);
        provider.setDefault_service(WriteService.class,double[].class,key);
        
        
        
        // Search Space 
        int DIMENSION = 2;
        double[] min = DoubleArrayInit.create(DIMENSION, -5.12);
        double[] max = DoubleArrayInit.create(DIMENSION, 5.12);
        InstanceService ikey = new DoubleArrayInstance(min, max);
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
        solution = (Solution<double[]>)InstanceProvider.get(solution);

        // Function being optimized
        OptimizationFunction function = new Griewangk(); 
        
        // Evaluating the fitness of the initial population
        solution.evaluate(function);

        double sigma = 0.02;
        ArityOne mutation = new EllipticMutation(DIMENSION, sigma,
//                new StandardGaussianGenerator());
                    new SimplestSymmetricPowerLawGenerator());

        // Hill Climbing
        SolutionTransformation transformation = new RandomOptimizer(mutation);

        // Evolution generations
        int MAXITER = 1000;
        Predicate condition = new ForLoopCondition(MAXITER);

        // Evolutionary algorithm (is a population optimizer)
        Optimizer optimizer = new IterativeOptimizer(condition,
                transformation, solution);

        // A console set tracer
        Tracer tracer = new ConsoleTracer(optimizer);
        // Adding the tracer collection to the given population optimizer (evolutionary algorithm)
        provider.register(tracer);
        
        tracer = new VectorTracer(optimizer, 10);
        provider.register(tracer);

        // running the population optimizer (the evolutionary algorithm)
        optimizer.apply(function);

        tracer.close();
 
        System.out.println("Vector tracer");
        Vector<double[]> tr = (Vector<double[]>)tracer.get();
        for( int i=0; i<tr.size(); i++){
            System.out.println( Persistency.toString(tr.get(i)));
        }        
    }    
}
