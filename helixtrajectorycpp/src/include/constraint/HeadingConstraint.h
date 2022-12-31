#pragma once

#include <optional>

#include "set/IntervalSet1d.h"
#include "solution/SolutionChecking.h"

namespace helixtrajectory {

class HeadingConstraint {
public:
    IntervalSet1d headingBound;

    HeadingConstraint(const IntervalSet1d& headingBound);

    std::optional<SolutionError> CheckHeading(double theta,
            const SolutionTolerances& tolerances) const noexcept;
};
}

template<>
struct fmt::formatter<helixtrajectory::HeadingConstraint> {

    template<typename ParseContext>
    constexpr auto parse(ParseContext& ctx);

    template<typename FormatContext>
    auto format(const helixtrajectory::HeadingConstraint& headingConstraint, FormatContext& ctx);
};