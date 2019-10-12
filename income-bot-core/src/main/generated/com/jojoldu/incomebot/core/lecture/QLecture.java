package com.jojoldu.incomebot.core.lecture;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = 537505527L;

    public static final QLecture lecture = new QLecture("lecture");

    public final com.jojoldu.incomebot.core.QBaseTimeEntity _super = new com.jojoldu.incomebot.core.QBaseTimeEntity(this);

    public final NumberPath<Long> beforeScore = createNumber("beforeScore", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> currentScore = createNumber("currentScore", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<LectureType> lectureType = createEnum("lectureType", LectureType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath url = createString("url");

    public QLecture(String variable) {
        super(Lecture.class, forVariable(variable));
    }

    public QLecture(Path<? extends Lecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLecture(PathMetadata metadata) {
        super(Lecture.class, metadata);
    }

}

