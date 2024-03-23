package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubMember is a Querydsl query type for ClubMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMember extends EntityPathBase<ClubMember> {

    private static final long serialVersionUID = 371099761L;

    public static final QClubMember clubMember = new QClubMember("clubMember");

    public final NumberPath<Long> attendanceStateId = createNumber("attendanceStateId", Long.class);

    public final NumberPath<Long> clubGradeId = createNumber("clubGradeId", Long.class);

    public final NumberPath<Long> clubId = createNumber("clubId", Long.class);

    public final NumberPath<Long> clubMemberId = createNumber("clubMemberId", Long.class);

    public final NumberPath<Long> clubMemberInformationId = createNumber("clubMemberInformationId", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QClubMember(String variable) {
        super(ClubMember.class, forVariable(variable));
    }

    public QClubMember(Path<? extends ClubMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubMember(PathMetadata metadata) {
        super(ClubMember.class, metadata);
    }

}
