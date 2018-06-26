package com.cmpl.web.core.association_entity_group;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAssociationEntityGroup is a Querydsl query type for AssociationEntityGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAssociationEntityGroup extends EntityPathBase<AssociationEntityGroup> {

    private static final long serialVersionUID = 1650290446L;

    public static final QAssociationEntityGroup associationEntityGroup = new QAssociationEntityGroup("associationEntityGroup");

    public final com.cmpl.web.core.common.dao.QBaseEntity _super = new com.cmpl.web.core.common.dao.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> creationDate = _super.creationDate;

    //inherited
    public final StringPath creationUser = _super.creationUser;

    public final StringPath entityId = createString("entityId");

    public final StringPath groupId = createString("groupId");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modificationDate = _super.modificationDate;

    //inherited
    public final StringPath modificationUser = _super.modificationUser;

    public QAssociationEntityGroup(String variable) {
        super(AssociationEntityGroup.class, forVariable(variable));
    }

    public QAssociationEntityGroup(Path<? extends AssociationEntityGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAssociationEntityGroup(PathMetadata metadata) {
        super(AssociationEntityGroup.class, metadata);
    }

}

