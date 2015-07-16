/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.algorithm.iterative.ForLoopCondition;
import unalcol.instance.InstanceProvider;
import unalcol.instance.InstanceService;
import unalcol.math.logic.Predicate;
import unalcol.optimization.iterative.IterativePopulationOptimizer;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.PopulationOptimizer;
import unalcol.types.collection.bitarray.BitArrayInstance;
import unalcol.optimization.operators.ArityOne;
import unalcol.optimization.operators.binary.Mutation;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionInstance;
import unalcol.optimization.testbed.binary.BoundedlyDeceptive;
import unalcol.optimization.testbed.binary.MaxOnes;
import unalcol.optimization.transformation.Transformation;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;
import unalcol.tracer.ConsoleTracer;
import unalcol.tracer.FileTracer;
import unalcol.tracer.Tracer;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public class BitArrayOptimizationDemo {
    public static void main( String[] args ){
        // Reflection
        ServiceProvider provider = ReflectUtil.getProvider("services/");

        // Search Space 
        int BITARRAYLENGTH = 500;
        BitArray array = new BitArray(BITARRAYLENGTH, true);
        InstanceService ikey = new BitArrayInstance();
        provider.register(ikey);
        provider.setDefault_service(InstanceService.class,BitArray.class,ikey);

        // Solution space
        Solution<BitArray> solution = new Solution<>(array);
        SolutionInstance skey = new SolutionInstance();
        provider.register(skey);
        provider.setDefault_service(InstanceService.class,Solution.class,skey);

        // Initial population
        int POPSIZE = 100;
        Vector<Solution<BitArray>> pop = InstanceProvider.get(solution, POPSIZE);

        // Function being optimized
        OptimizationFunction function = new MaxOnes();
        // Evaluating the fitness of the initial population
        Solution.evaluate((Vector)pop, function);

        //ArityOne mutation = new SphereGaussianMutation(0.2);
        ArityOne mutation = new Mutation();

        // Hill Climbing
        Transformation transformation = new HillClimbing(mutation);

        // Evolution generations
        int MAXITER = 100;
        Predicate condition = new ForLoopCondition(MAXITER);

        // Evolutionary algorithm (is a population optimizer)
        PopulationOptimizer optimizer = new IterativePopulationOptimizer(condition,
                transformation, pop);

        // A console set tracer
        Tracer tracer = new ConsoleTracer(optimizer);
        // Adding the tracer collection to the given population optimizer (evolutionary algorithm)
        provider.register(tracer);
        tracer = new FileTracer(optimizer, "hc.txt", true);
        provider.register(tracer);

        // running the population optimizer (the evolutionary algorithm)
        optimizer.apply(function);

        tracer.close();
 
    }
    
}
