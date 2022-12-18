//package com.lovetotravel.travel.travelnet.controller;
//
//
//import com.lovetotravel.travel.travelnet.entity.Scenery;
//import org.neo4j.driver.Driver;
//import org.neo4j.driver.Metrics;
//import org.neo4j.driver.Session;
//import org.neo4j.driver.SessionConfig;
//import org.neo4j.driver.async.AsyncSession;
//import org.neo4j.driver.reactive.RxSession;
//import org.neo4j.driver.types.TypeSystem;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.neo4j.core.Neo4jClient;
//import org.springframework.data.neo4j.core.Neo4jOperations;
//import org.springframework.data.neo4j.core.Neo4jTemplate;
//import org.springframework.data.neo4j.repository.query.QueryFragmentsAndParameters;
//
//import java.util.*;
//import java.util.concurrent.CompletionStage;
//
//public class test {
//
//    public static void main(String[] args) {
////        Neo4jTemplate neo4jTemplate = ;
//
//
//            Optional<Scenery> scenery;
//            // 1. 通
//
//            Driver driver = new Driver() {
//                @Override
//                public boolean isEncrypted() {
//                    return false;
//                }
//
//                @Override
//                public Session session() {
//                    return null;
//                }
//
//                @Override
//                public Session session(SessionConfig sessionConfig) {
//                    return null;
//                }
//
//                @Override
//                public RxSession rxSession() {
//                    return null;
//                }
//
//                @Override
//                public RxSession rxSession(SessionConfig sessionConfig) {
//                    return null;
//                }
//
//                @Override
//                public AsyncSession asyncSession() {
//                    return null;
//                }
//
//                @Override
//                public AsyncSession asyncSession(SessionConfig sessionConfig) {
//                    return null;
//                }
//
//                @Override
//                public void close() {
//
//                }
//
//                @Override
//                public CompletionStage<Void> closeAsync() {
//                    return null;
//                }
//
//                @Override
//                public Metrics metrics() {
//                    return null;
//                }
//
//                @Override
//                public boolean isMetricsEnabled() {
//                    return false;
//                }
//
//                @Override
//                public TypeSystem defaultTypeSystem() {
//                    return null;
//                }
//
//                @Override
//                public void verifyConnectivity() {
//
//                }
//
//                @Override
//                public CompletionStage<Void> verifyConnectivityAsync() {
//                    return null;
//                }
//
//                @Override
//                public boolean supportsMultiDb() {
//                    return false;
//                }
//
//                @Override
//                public CompletionStage<Boolean> supportsMultiDbAsync() {
//                    return null;
//                }
//            };
//
//
//            Neo4jTemplate neo4jTemplate = new Neo4jTemplate(Neo4jClient.with(driver).build());
//
//        System.out.println(neo4jTemplate);
//
//            scenery = neo4jTemplate.findById(12, Scenery.class);
//            System.out.println("id为12号的Person节点：\n" + scenery);
//
//            // 2. 通过属性查询节点，如name 需要手写cypherQuery语句
//            Map<String, Object> map = new HashMap<>();
//            map.put("name", "新海诚");
//            // 两种写法都对，看个人喜好 n是一个变量随意取，{}或者where填写query的filter过滤条件
//            scenery = neo4jTemplate.findOne("MATCH (n:Person {name: $name}) RETURN n", map, Scenery.class);
//                    scenery = neo4jTemplate.findOne("MATCH (n:Person) WHERE n.name = $name RETURN n",map, Scenery.class);
//            System.out.println("\n查询名字为新海诚的Person节点:\n" + scenery);
//
//            // 3. 通过属性关系查询节点
//            map = new HashMap<>();
//            map.put("roles", Collections.singletonList("宫水三叶"));
//            // 方法1.使用toExecutableQuery查询
//            QueryFragmentsAndParameters parameters = new QueryFragmentsAndParameters(
//                    "MATCH (person:Person) -[ relation:ACTED_IN]-> (movie:Movie) \n" +
//                            "WHERE relation.roles = $roles\n" +
//                            "RETURN person", map);
//
////            List<Scenery> roles = neo4jTemplate.toExecutableQuery(Scenery.class, parameters).getResults();
//            // 方法2.使用findOne查询
//            Optional<Scenery> roles = neo4jTemplate.findOne(
//                            "MATCH (person:Person) -[ relation:ACTED_IN]-> (movie:Movie) \n" +
//                            "WHERE relation.roles = $roles\n" +
//                            "RETURN person",map,Scenery.class);
//
//            System.out.println("\n查询角色为“宫水三叶”的演员：\n"+roles);
//
//        }// 查询（不太推荐用Neo4jTemplate进行过滤查询，因为需要手动写cypherQuery,需要开发人员了解一下cypherQuery的写法）
//
//
//
//}
//
