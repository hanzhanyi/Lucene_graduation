package com.forestWolf.luceneGraduation.lucene;

import com.forestWolf.luceneGraduation.enums.CropNameEnums;
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
 *
 * @author Administrator
 */
public class CropIndex {

    private Directory dir = null;


    /**
     * 获取IndexWriter实例
     *
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter() throws Exception {
        File file = new File("./lucene");
        if (!file.exists()) {
            file.mkdir();
        }
        dir = FSDirectory.open(Paths.get(file.getPath()));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加索引
     *
     * @param cropDetail
     */
    public void addIndex(CropDetail cropDetail) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();

        doc.add(new StringField(CropNameEnums.ID.getCropName(), String.valueOf(cropDetail.getId()), Field.Store.YES));
        doc.add(new StringField(CropNameEnums.CROP_NAME.getCropName(), cropDetail.getCropName(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.TOTAL_TEXT.getCropName(), cropDetail.getEnglishName() + cropDetail.getIntroduction()
                + cropDetail.getDamageSym() + cropDetail.getOccurrenceFactor() + cropDetail.getMorphology() + cropDetail.getHabits()
                + cropDetail.getHandleMethod() + cropDetail.getAnotherName() + cropDetail.getPathogen() + cropDetail.getCycle()
                , Field.Store.YES));
        doc.add(new TextField(CropNameEnums.ENGLISH_NAME.getCropName(), cropDetail.getEnglishName(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.INTRODUCTION.getCropName(), cropDetail.getIntroduction(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.DAMAGE_SYM.getCropName(), cropDetail.getDamageSym(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.OCCURRENCE_FACTOR.getCropName(), cropDetail.getOccurrenceFactor(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.MORPHOLOGY.getCropName(), cropDetail.getMorphology(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.HABITS.getCropName(), cropDetail.getHabits(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.HANDLE_METHOD.getCropName(), cropDetail.getHandleMethod(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.ANOTHER_NAME.getCropName(), cropDetail.getAnotherName(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.PATHOGEN.getCropName(), cropDetail.getPathogen(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.CYCLE.getCropName(), cropDetail.getCycle(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.CROP_TYPE.getCropName(), cropDetail.getCropType().toString(), Field.Store.YES));


        System.out.println("正在建立索引 " + cropDetail.getCropName());
        writer.addDocument(doc);
        writer.close();
    }

    /**
     * 更新索引
     *
     * @param cropDetail
     * @throws Exception
     */
    public void updateIndex(CropDetail cropDetail) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField(CropNameEnums.ID.getCropName(), String.valueOf(cropDetail.getId()), Field.Store.YES));
        doc.add(new StringField(CropNameEnums.CROP_NAME.getCropName(), cropDetail.getCropName(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.TOTAL_TEXT.getCropName(), cropDetail.getEnglishName() + cropDetail.getIntroduction()
                + cropDetail.getDamageSym() + cropDetail.getOccurrenceFactor() + cropDetail.getMorphology() + cropDetail.getHabits()
                + cropDetail.getHandleMethod() + cropDetail.getAnotherName() + cropDetail.getPathogen() + cropDetail.getCycle()
                , Field.Store.YES));
        doc.add(new TextField(CropNameEnums.ENGLISH_NAME.getCropName(), cropDetail.getEnglishName(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.INTRODUCTION.getCropName(), cropDetail.getIntroduction(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.DAMAGE_SYM.getCropName(), cropDetail.getDamageSym(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.OCCURRENCE_FACTOR.getCropName(), cropDetail.getOccurrenceFactor(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.MORPHOLOGY.getCropName(), cropDetail.getMorphology(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.HABITS.getCropName(), cropDetail.getHabits(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.HANDLE_METHOD.getCropName(), cropDetail.getHandleMethod(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.ANOTHER_NAME.getCropName(), cropDetail.getAnotherName(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.PATHOGEN.getCropName(), cropDetail.getPathogen(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.CYCLE.getCropName(), cropDetail.getCycle(), Field.Store.YES));
        doc.add(new TextField(CropNameEnums.CROP_TYPE.getCropName(), cropDetail.getCropType().toString(), Field.Store.YES));

        writer.updateDocument(new Term("id", String.valueOf(cropDetail.getId())), doc);
        writer.close();
    }

    /**
     * 删除指定的索引
     *
     * @param id
     * @throws Exception
     */
    public void deleteIndex(String id) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", id));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }

    /**
     * 删除所有的索引
     *
     * @throws Exception
     */
    public void deleteIndexAll() throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteAll();
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
        System.out.println("成功删除索引");
    }

    /**
     * 查询信息
     *
     * @param q 查询关键字
     * @return
     * @throws Exception
     */
    public List<CropDetail> searchCrop(String q) throws Exception {
        File file = new File("./lucene");
        if (!file.exists()) {
            file.mkdir();
        }
        dir = FSDirectory.open(Paths.get(file.getPath()));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        QueryParser cropNameParser = new QueryParser(CropNameEnums.CROP_NAME.getCropName(), analyzer);
        Query cropNameQuery = cropNameParser.parse(q);
        QueryParser introductionParser = new QueryParser(CropNameEnums.TOTAL_TEXT.getCropName(), analyzer);
        Query introductionQuery = introductionParser.parse(q);

        booleanQuery.add(cropNameQuery, BooleanClause.Occur.SHOULD);
        booleanQuery.add(introductionQuery, BooleanClause.Occur.SHOULD);

        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(introductionQuery);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<CropDetail> blogList = new LinkedList<CropDetail>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            CropDetail cropDetail = new CropDetail();
            cropDetail.setId(Long.parseLong(doc.get((CropNameEnums.ID.getCropName()))));
            String cropName = doc.get(CropNameEnums.CROP_NAME.getCropName());
            String totalText = doc.get(CropNameEnums.TOTAL_TEXT.getCropName());
            if (cropName != null) {
                TokenStream tokenStream = analyzer.tokenStream(CropNameEnums.CROP_NAME.getCropName(), new StringReader(cropName));
                String hCropName = highlighter.getBestFragment(tokenStream, cropName);
                if (Strings.isNullOrEmpty(hCropName)) {
                    cropDetail.setCropName(cropName);
                } else {
                    cropDetail.setCropName(hCropName);
                }
            }
            if (totalText != null) {
                TokenStream tokenStream = analyzer.tokenStream(CropNameEnums.TOTAL_TEXT.getCropName(), new StringReader(totalText));
                String hIntroduction = highlighter.getBestFragment(tokenStream, totalText);
                if (Strings.isNullOrEmpty(hIntroduction)) {
                    if (totalText.length() <= 200) {
                        cropDetail.setIntroduction(totalText);
                    } else {
                        cropDetail.setIntroduction(totalText.substring(0, 200));
                    }
                } else {
                    System.out.println(hIntroduction);
                    cropDetail.setIntroduction(hIntroduction);
                }
            }
            blogList.add(cropDetail);
        }
        return blogList;
    }

    /**
     * 高级查询信息
     *
     * @param q 查询关键字
     * @return
     * @throws Exception
     */
    public List<CropDetail> seniorSearchIndex(String q) throws Exception {
        File file = new File("./lucene");
        if (!file.exists()) {
            file.mkdir();
        }
        dir = FSDirectory.open(Paths.get(file.getPath()));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        QueryParser introductionParser = new QueryParser(CropNameEnums.TOTAL_TEXT.getCropName(), analyzer);
        Query introductionQuery = introductionParser.parse(q);
        booleanQuery.add(introductionQuery, BooleanClause.Occur.SHOULD);
        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(introductionQuery);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<CropDetail> blogList = new LinkedList<CropDetail>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            CropDetail cropDetail = new CropDetail();
            cropDetail.setId(Long.parseLong(doc.get((CropNameEnums.ID.getCropName()))));
            String cropName = doc.get(CropNameEnums.CROP_NAME.getCropName());
            String introduction = doc.get(CropNameEnums.INTRODUCTION.getCropName());
            if (cropName != null) {
                cropDetail.setCropName(cropName);
            }
            if (introduction != null) {
                TokenStream tokenStream = analyzer.tokenStream(CropNameEnums.INTRODUCTION.getCropName(), new StringReader(introduction));
                String hIntroduction = highlighter.getBestFragment(tokenStream, introduction);
                if (Strings.isNullOrEmpty(hIntroduction)) {
                    if (introduction.length() <= 200) {
                        cropDetail.setIntroduction(introduction);
                    } else {
                        cropDetail.setIntroduction(introduction.substring(0, 200));
                    }
                } else {
                    System.out.println(hIntroduction);
                    cropDetail.setIntroduction(hIntroduction);
                }
            }
            blogList.add(cropDetail);
        }
        return blogList;
    }

    /**
     * 专业查询信息
     *
     * @param q  查询关键字
     * @param no 对应ID值
     * @return
     * @throws Exception
     */
    public List<CropDetail> profSearchCrop(String q, int no) throws Exception {
        File file = new File("./lucene");
        if (!file.exists()) {
            file.mkdir();
        }
        dir = FSDirectory.open(Paths.get(file.getPath()));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        QueryParser cropNameParser = new QueryParser(CropNameEnums.CROP_NAME.getCropName(), analyzer);
        Query cropNameQuery = cropNameParser.parse(q);
        CropNameEnums cropNameEnum = CropNameEnums.getCropNameEnumById(no);
        List<CropDetail> blogList = new LinkedList<CropDetail>();

        if (cropNameEnum != null) {
            QueryParser introductionParser = new QueryParser(cropNameEnum.getCropName(), analyzer);
            Query introductionQuery = introductionParser.parse(q);
            booleanQuery.add(introductionQuery, BooleanClause.Occur.SHOULD);
            booleanQuery.add(cropNameQuery, BooleanClause.Occur.SHOULD);
            TopDocs hits = is.search(booleanQuery.build(), 100);
            QueryScorer scorer = new QueryScorer(introductionQuery);
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
            highlighter.setTextFragmenter(fragmenter);

            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = is.doc(scoreDoc.doc);
                CropDetail cropDetail = new CropDetail();
                cropDetail.setId(Long.parseLong(doc.get((CropNameEnums.ID.getCropName()))));
                String cropName = doc.get(CropNameEnums.CROP_NAME.getCropName());
                String context = doc.get(cropNameEnum.getCropName());
                if (cropName != null) {
                    TokenStream tokenStream = analyzer.tokenStream(CropNameEnums.CROP_NAME.getCropName(), new StringReader(cropName));
                    String hCropName = highlighter.getBestFragment(tokenStream, cropName);
                    if (Strings.isNullOrEmpty(hCropName)) {
                        cropDetail.setCropName(cropName);
                    } else {
                        cropDetail.setCropName(hCropName);
                    }
                }
                if (context != null) {
                    TokenStream tokenStream = analyzer.tokenStream(cropNameEnum.getCropName(), new StringReader(context));
                    String hIntroduction = highlighter.getBestFragment(tokenStream, context);
                    if (Strings.isNullOrEmpty(hIntroduction)) {
                        if (context.length() <= 200) {
                            cropDetail.setIntroduction(context);
                        } else {
                            cropDetail.setIntroduction(context.substring(0, 200));
                        }
                    } else {
                        System.out.println(hIntroduction);
                        cropDetail.setIntroduction(hIntroduction);
                    }
                }
                blogList.add(cropDetail);
            }
        }
        return blogList;
    }
}
