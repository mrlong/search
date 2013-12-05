/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


import org.wltea.analyzer.lucene.IKAnalyzer;

import net.yunzj.demo.IKTester;


/**
 * The simplest possible servlet.
 *
 * @author James Duncan Davidson
 */

public class HelloWorldExample extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        //ResourceBundle rb =
        //ResourceBundle.getBundle("LocalStrings",request.getLocale());
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        
        IKTester mytest = new IKTester();

        PrintWriter out = response.getWriter();
        String title="1HELLO在要地地 mrlong" + "sssss" + mytest.gettext();
        out.println("<html>");
        out.println("<head>");

        //String title = rb.getString("helloworld.title");

        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");

        // note that all links are created to be relative. this
        // ensures that we can move the web application that this
        // servlet belongs to to a different place in the url
        // tree and not have any harmful side effects.

        // XXX
        // making these absolute till we work out the
        // addition of a PathInfo issue

        
        out.println("<h1>" + title + "</h1>");


        //Lucene Document的域名
        String fieldName = "text";
        //检索内容
        String text = "IK Analyzer是一个结合词使用了全新的正向迭代最细粒度切分算法。";

        Analyzer analyzer = new IKAnalyzer();

        Directory directory = null;
        IndexWriter iwriter = null;


        directory = new RAMDirectory();
        IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_34 , analyzer);
        iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);

        iwriter = new IndexWriter(directory , iwConfig);
        
        Document doc = new Document();
        doc.add(new Field("ID", "10000", Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.add(new Field(fieldName, text, Field.Store.YES,Field.Index.ANALYZED));
        iwriter.addDocument(doc);
        iwriter.close();

        // IndexWriter writer = new IndexWriter("c:/data/index",/*new StandardAnalyzer()*/analyzer,true);
        // Document doc = new Document();
        // doc.add(new Field("title", "lucene introduction", Field.Store.YES, Field.Index.TOKENIZED));
        // doc.add(new Field("content", "lucene works well", Field.Store.YES, Field.Index.TOKENIZED));
        // writer.addDocument(doc);
        // writer.optimize();
        // writer.close();

        IndexReader ireader = null;
        IndexSearcher isearcher = null;

        ireader = IndexReader.open(directory);
        isearcher = new IndexSearcher(ireader);
        String keyword = "IK";
        //使用QueryParser查询分析器构造Query对象
        

        try {
            QueryParser qp = new QueryParser(Version.LUCENE_34,fieldName, analyzer);
            qp.setDefaultOperator(QueryParser.AND_OPERATOR);

            Query query = qp.parse(keyword);

            //搜索相似度最高的5条记录
            TopDocs topDocs = isearcher.search(query,5);
            out.println("命中：" + topDocs.totalHits);
            //输出结果
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < topDocs.totalHits; i++){
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                out.println("内容：" + targetDoc.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ireader.close();
            directory.close();
        }


        


        out.println("</body>");
        out.println("</html>");
    }
}



