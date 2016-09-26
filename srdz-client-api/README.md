# Restful APi + Swagger UI基础框架

---

## 1 注解文档
https://github.com/swagger-api/swagger-core/wiki/Annotations

## 2 关于spring data jpa helper类的说明
        // JpaHelper的使用方法，已经对字段进行了非空验证
        JpaHelper<UserInfo> jpaHelper = new JpaHelper<>();
		if(userInfo != null) {
            Specification<UserInfo> restrictions = Specifications.
                    where(jpaHelper.like("userTruename", userInfo.getUserTruename())).
                    and(jpaHelper.eq("roleId", userInfo.getRoleId())).
                    and(jpaHelper.eq("userState", userInfo.getUserState()));
            Page<UserInfo> page = userInfoDao.findAll(restrictions, pageable);
        }

## 3 关于querydsl，是jpa,hibernate等的上层封装，一个统一的api，但目前没有找到nullsafe的用法，字段还需要手动去验证
        JPAQuery query = new JPAQuery(em);
        QUserInfo qUserInfo = QUserInfo.userInfo;
        BooleanBuilder builder = new BooleanBuilder();
        if (userInfo != null) {
            if(!StringUtils.isEmpty(userInfo.getUserTruename())) {
                builder.and(qUserInfo.userTruename.like("%" + userInfo.getUserTruename() + "%"));
            }
            if(!StringUtils.isEmpty(userInfo.getRoleId())) {
                builder.and(qUserInfo.roleId.eq(userInfo.getRoleId()));
            }
            if(!StringUtils.isEmpty(userInfo.getUserState())) {
                builder.and(qUserInfo.userState.eq(userInfo.getUserState()));
            }
        }
        List<UserInfo> resultList = query.from(qUserInfo).where(builder).list(qUserInfo);
        new PageImpl<>(resultList, pageable, resultList.size());
