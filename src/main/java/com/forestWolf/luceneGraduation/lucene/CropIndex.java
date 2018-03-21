package com.forestWolf.luceneGraduation.lucene;

import com.forestWolf.luceneGraduation.pojo.CropDetail;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客索引类
 * @author Administrator
 *
 */
public class CropIndex {

    private Directory dir=null;


    /**
     * 获取IndexWriter实例
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter()throws Exception{
        File file = new File("./lucene");
        if(!file.exists()){
            file.mkdir();
        }
        dir=FSDirectory.open(Paths.get(file.getPath()));
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加索引
     * @param cropDetail
     */
    public void addIndex(CropDetail cropDetail)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(cropDetail.getId()),Field.Store.YES));
        doc.add(new StringField("cropName",cropDetail.getCropName(), Field.Store.YES));
        doc.add(new TextField("introduction",cropDetail.getEnglishName()+cropDetail.getIntroduction()
                +cropDetail.getDamageSym()+cropDetail.getOccurrenceFactor()+cropDetail.getMorphology()+cropDetail.getHabits()
                +cropDetail.getHandleMethod()+cropDetail.getAnotherName()+cropDetail.getPathogen()+cropDetail.getCycle()
                ,Field.Store.YES));
//        doc.add(new TextField("damageSym",cropDetail.getDamageSym(),Field.Store.YES));
//        doc.add(new TextField("occurrenceFactor",cropDetail.getOccurrenceFactor(),Field.Store.YES));
//        doc.add(new TextField("morphology",cropDetail.getMorphology(),Field.Store.YES));
//        doc.add(new TextField("habits",cropDetail.getHabits(),Field.Store.YES));
//        doc.add(new TextField("handleMethod",cropDetail.getHandleMethod(),Field.Store.YES));
//        doc.add(new TextField("anotherName",cropDetail.getAnotherName(),Field.Store.YES));
//        doc.add(new TextField("pathogen",cropDetail.getPathogen(),Field.Store.YES));
//        doc.add(new TextField("cycle",cropDetail.getCycle(),Field.Store.YES));
        System.out.println("正在建立索引 "+cropDetail.getCropName());
        writer.addDocument(doc);
        writer.close();
    }

    /**
     * 更新索引
     * @param cropDetail
     * @throws Exception
     */
    public void updateIndex(CropDetail cropDetail)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(cropDetail.getId()),Field.Store.YES));
        doc.add(new StringField("cropName",cropDetail.getCropName(), Field.Store.YES));
        doc.add(new TextField("introduction",cropDetail.getEnglishName()+cropDetail.getIntroduction()
                +cropDetail.getDamageSym()+cropDetail.getOccurrenceFactor()+cropDetail.getMorphology()+cropDetail.getHabits()
                +cropDetail.getHandleMethod()+cropDetail.getAnotherName()+cropDetail.getPathogen()+cropDetail.getCycle()
                ,Field.Store.YES));

//        doc.add(new StringField("id",String.valueOf(cropDetail.getId()),Field.Store.YES));
//        doc.add(new StringField("cropName",cropDetail.getCropName(), Field.Store.YES));
//        doc.add(new TextField("englishName",cropDetail.getEnglishName(),Field.Store.YES));
//        doc.add(new TextField("introduction",cropDetail.getIntroduction(),Field.Store.YES));
//        doc.add(new TextField("damageSym",cropDetail.getDamageSym(),Field.Store.YES));
//        doc.add(new TextField("occurrenceFactor",cropDetail.getOccurrenceFactor(),Field.Store.YES));
//        doc.add(new TextField("morphology",cropDetail.getMorphology(),Field.Store.YES));
//        doc.add(new TextField("habits",cropDetail.getHabits(),Field.Store.YES));
//        doc.add(new TextField("handleMethod",cropDetail.getHandleMethod(),Field.Store.YES));
//        doc.add(new TextField("anotherName",cropDetail.getAnotherName(),Field.Store.YES));
//        doc.add(new TextField("pathogen",cropDetail.getPathogen(),Field.Store.YES));
//        doc.add(new TextField("cycle",cropDetail.getCycle(),Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(cropDetail.getId())), doc);
        writer.close();
    }

    /**
     * 删除指定的索引
     * @param id
     * @throws Exception
     */
    public void deleteIndex(String id)throws Exception{
        IndexWriter writer=getWriter();
        writer.deleteDocuments(new Term("id",id));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }

    /**
     * 删除所有的索引
     * @throws Exception
     */
    public void deleteIndexAll()throws Exception{
        IndexWriter writer=getWriter();
        writer.deleteAll();
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
        System.out.println("成功删除索引");
    }

    /**
     * 查询信息
     * @param q 查询关键字
     * @return
     * @throws Exception
     */
    public List<CropDetail> searchCrop(String q)throws Exception{
        File file = new File("./lucene");
        if(!file.exists()){
            file.mkdir();
        }
        dir=FSDirectory.open(Paths.get(file.getPath()));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is=new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();

//        QueryParser cropNameParser=new QueryParser("cropName",analyzer);
//        Query cropNameQuery=cropNameParser.parse(q);
//        QueryParser englishNameParser=new QueryParser("englishName",analyzer);
//        Query englishNameQuery=englishNameParser.parse(q);
//        QueryParser introductionParser=new QueryParser("introduction",analyzer);
//        Query introductionQuery=introductionParser.parse(q);
//        QueryParser damageSymParser=new QueryParser("damageSym",analyzer);
//        Query damageSymQuery=damageSymParser.parse(q);
//        QueryParser occurrenceFactorParser=new QueryParser("occurrenceFactor",analyzer);
//        Query occurrenceFactorQuery=occurrenceFactorParser.parse(q);
//        QueryParser morphologyParser=new QueryParser("morphology",analyzer);
//        Query morphologyQuery=morphologyParser.parse(q);
//        QueryParser habitsParser=new QueryParser("habits",analyzer);
//        Query habitsQuery=habitsParser.parse(q);
//        QueryParser handleMethodParser=new QueryParser("handleMethod",analyzer);
//        Query handleMethodQuery=handleMethodParser.parse(q);
//        QueryParser anotherNameParser=new QueryParser("anotherName",analyzer);
//        Query anotherNameQuery=anotherNameParser.parse(q);
//        QueryParser pathogenParser=new QueryParser("pathogen",analyzer);
//        Query pathogenQuery=pathogenParser.parse(q);
//        QueryParser cycleParser=new QueryParser("cycle",analyzer);
//        Query cycleQuery=cycleParser.parse(q);

//        booleanQuery.add(cropNameQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(englishNameQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(introductionQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(damageSymQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(occurrenceFactorQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(morphologyQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(habitsQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(handleMethodQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(anotherNameQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(pathogenQuery,BooleanClause.Occur.SHOULD);
//        booleanQuery.add(cycleQuery,BooleanClause.Occur.SHOULD);

        QueryParser cropNameParser=new QueryParser("cropName",analyzer);
        Query cropNameQuery=cropNameParser.parse(q);
        QueryParser introductionParser=new QueryParser("introduction",analyzer);
        Query introductionQuery=introductionParser.parse(q);

        booleanQuery.add(cropNameQuery,BooleanClause.Occur.SHOULD);
        booleanQuery.add(introductionQuery,BooleanClause.Occur.SHOULD);

        TopDocs hits=is.search(booleanQuery.build(), 100);
        QueryScorer scorer=new QueryScorer(introductionQuery);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<CropDetail> blogList=new LinkedList<CropDetail>();
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            CropDetail cropDetail=new CropDetail();
            cropDetail.setId(Long.parseLong(doc.get(("id"))));
            String cropName=doc.get("cropName");
            String introduction=doc.get("introduction");
            if(cropName!=null){
                TokenStream tokenStream = analyzer.tokenStream("cropName", new StringReader(cropName));
                String hCropName=highlighter.getBestFragment(tokenStream, cropName);
                if(Strings.isNullOrEmpty(hCropName)){
                    cropDetail.setCropName(cropName);
                }else{
                    cropDetail.setCropName(hCropName);
                }
            }
            if(introduction!=null){
                TokenStream tokenStream = analyzer.tokenStream("introduction", new StringReader(introduction));
                String hIntroduction=highlighter.getBestFragment(tokenStream, introduction);
                if(Strings.isNullOrEmpty(hIntroduction)){
                    if(introduction.length()<=200){
                        cropDetail.setIntroduction(introduction);
                    }else{
                        cropDetail.setIntroduction(introduction.substring(0, 200));
                    }
                }else{
                    System.out.println(hIntroduction);
                    cropDetail.setIntroduction(hIntroduction);
                }
            }
            blogList.add(cropDetail);
        }
        return blogList;
    }
}
