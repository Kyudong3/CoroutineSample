package com.example.coroutinesample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    private val dispatcher = newSingleThreadContext(name = "ServiceCall")
    private val poolDispatcher = newFixedThreadPoolContext(2, "IO")
    private val factory = DocumentBuilderFactory.newInstance()

    private lateinit var feeds: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        GlobalScope.launch(dispatcher) {
//            loadNews()
//        }

        feeds = listOf(
            "https://www.npr.org/rss/rss.php?id=1001",
            "http://rss.cnn.com/rss/cnn_topstories.rss",
            "http://feeds.foxnews.com/foxnews/politics?format=xml",
            "htt:myNewsFeed"
        )

        asyncLoadNews()
    }

    /* 비동기 호출자로 감싼 동기 함수 */
    private fun loadNews() {
//        UI 업데이트가 있는데 UI Thread 가 아니기에 CalledFromWrongThreadException 발생
//        GlobalScope.launch(dispatcher) {
//            val headlines = fetchRssHeadlines()
//            newsCount.text = "Found ${headlines.size} News"
//        }
        val headlines = fetchRssHeadlines()
        GlobalScope.launch(Dispatchers.Main) {
            newsCount.text = "Found ${headlines.size} News"
        }
    }

    /* 미리 정의된 Dispatcher를 갖는 비동기 함수
    *  함수는 스레드와 상관없이 launch() 블록이 없는 상태로 호출될 수 있고, Job을 반환해서 호출자가 취소 가능 */
    private fun asyncLoadNews() = GlobalScope.launch {
        val requests = mutableListOf<Deferred<List<String>>>()
        feeds.mapTo(requests) {
            fetchHeadlinesAsync(it, poolDispatcher)
        }

        requests.forEach {
            //it.await()
            it.join()
        }

        val headlines = requests
            .filter { !it.isCancelled }
            .flatMap { it.getCompleted() }

        val failed = requests
            .filter { it.isCancelled }
            .size

        val obtained = requests.size - failed

        launch(Dispatchers.Main) {
            newsCount.text = "Found ${headlines.size} News " + "in ${requests.size} feeds"

            if (failed > 0) {
                warnings.text = "Failed to fetch $failed feeds"
            }
        }
    }

    private fun fetchRssHeadlines(): List<String> {
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse("https://www.npr.org/rss/rss.php?id=1001")
        val news = xml.getElementsByTagName("channel").item(0)
        return (0 until news.childNodes.length)
            .map { news.childNodes.item(it) }
            .filter { Node.ELEMENT_NODE == it.nodeType }
            .map { it as Element }
            .filter { "item" == it.tagName }
            .map {
                it.getElementsByTagName("title").item(0).textContent
            }
    }

    private fun fetchHeadlinesAsync(
        feed: String,
        dispatcher: CoroutineDispatcher
    ) = GlobalScope.async(dispatcher) {
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse(feed)
        val news = xml.getElementsByTagName("channel").item(0)
        (0 until news.childNodes.length)
            .map { news.childNodes.item(it) }
            .filter { Node.ELEMENT_NODE == it.nodeType }
            .map { it as Element }
            .filter { "item" == it.tagName }
            .map {
                it.getElementsByTagName("title").item(0).textContent
            }
    }
}