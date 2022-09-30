package org.team2363.helixtrajectory;

import java.io.IOException;
import java.util.Objects;

import org.team2363.util.DependencyExtractor;
import org.team2363.util.RuntimeLoader;

public final class OptimalTrajectoryGenerator {

    private static boolean isPluginLoaded = false;

    private static void loadPlugin() throws PluginLoadException {
        if (!isPluginLoaded) {
            try {
                var casadiLoader = new DependencyExtractor<OptimalTrajectoryGenerator>(
                        "casadi", RuntimeLoader.getDefaultExtractionRoot(), OptimalTrajectoryGenerator.class);
                var quadmathLoader = new DependencyExtractor<OptimalTrajectoryGenerator>(
                        "quadmath.0", RuntimeLoader.getDefaultExtractionRoot(), OptimalTrajectoryGenerator.class);
                var gfortranLoader = new DependencyExtractor<OptimalTrajectoryGenerator>(
                        "gfortran.4", RuntimeLoader.getDefaultExtractionRoot(), OptimalTrajectoryGenerator.class);
                var ipoptLoader = new DependencyExtractor<OptimalTrajectoryGenerator>(
                        "casadi_nlpsol_ipopt", RuntimeLoader.getDefaultExtractionRoot(), OptimalTrajectoryGenerator.class);
                var htLoader = new RuntimeLoader<OptimalTrajectoryGenerator>(
                        "helixtrajectory", RuntimeLoader.getDefaultExtractionRoot(), OptimalTrajectoryGenerator.class);
                casadiLoader.loadLibrary();
                quadmathLoader.loadLibrary();
                gfortranLoader.loadLibrary();
                ipoptLoader.loadLibrary();
                htLoader.loadLibrary();
                isPluginLoaded = true;
            } catch (IOException ioe) {
                throw new PluginLoadException("Could not load HelixTrajectory: " + ioe.getMessage(), ioe);
            }
        }
    }

    private OptimalTrajectoryGenerator() throws PluginLoadException {
        loadPlugin();
    }

    private native HolonomicTrajectory generateHolonomicTrajectory(SwerveDrivetrain swerveDrivetrain,
            HolonomicPath holonomicPath) throws InvalidPathException, TrajectoryGenerationException;

    public static HolonomicTrajectory generate(SwerveDrivetrain swerveDrivetrain, HolonomicPath holonomicPath)
            throws NullPointerException, InvalidPathException, PluginLoadException, TrajectoryGenerationException {

        return new OptimalTrajectoryGenerator().generateHolonomicTrajectory(
                Objects.requireNonNull(swerveDrivetrain, "Holonomic Trajectory Generator swerve drivetrain cannot be null"),
                Objects.requireNonNull(holonomicPath, "Holonomic Trajectory Generator holonomic path cannot be null"));
    }
}