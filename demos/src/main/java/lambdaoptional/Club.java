package lambdaoptional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Club {

    private List<Member> members;

    public Club(List<Member> members) {
        this.members = members;
    }

    public Optional<Member> findFirst(Predicate<Member> predicate) {
        for (Member actualMember : members) {
            if (predicate.test(actualMember)) {
                return Optional.of(actualMember);
            }
        }
        return Optional.empty();
    }

    public Optional<Double> averageNumberOfSkills() {
        if (members.isEmpty()) {
            return Optional.empty();
        }
        int skillsSum = 0;
        for (Member actualMember : members) {
            skillsSum += actualMember.getSkills().size();
        }
        return Optional.of(skillsSum / (double) members.size());
    }
}
