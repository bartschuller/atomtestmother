import java.util.Date
import org.apache.abdera.Abdera
import org.apache.abdera.model.{Document, Entry, Workspace, Service, Collection => ACollection}
import org.apache.abdera.protocol.client.AbderaClient
import org.specs2.mutable._
import org.specs2.specification.{Scope, Outside}

// See http://svn.apache.org/repos/asf/abdera/java/trunk/examples/src/main/java/org/apache/abdera/examples/appclient/Main.java

class State {
  val url = "http://localhost:9988/"
  val abdera = new Abdera
  val abderaClient = new AbderaClient(abdera)
  val factory = abdera.getFactory
  var service: Service = _
  var workspace: Workspace = _
  var collection: ACollection = _
  var doc: Document[Entry] = _
}
object State extends State

class BartomSpec extends Specification {
  args(sequential=true) // Bart is a bad boy.
  implicit object state extends Outside[State] with Scope { def outside = State }

  "Bart's Atom Server" should {
    "Support introspection" in { (s: State) =>
      s.service = s.abderaClient.get(s.url).getDocument.getRoot
      s.service must not beNull
    }
    "Have a workspace called 'Main Site'" in { (s: State) =>
      s.workspace = s.service.getWorkspace("Main Site")
      s.workspace must not beNull
    }
    "Have a collection called 'My Blog Entries'" in { (s: State) =>
      s.collection = s.workspace.getCollection("My Blog Entries")
      s.collection must not beNull
    }
    "Allow posting a document" in { (s: State) =>
      val entry = s.factory.newEntry()
      entry.setId("tag:example.org,2006:foo")
      entry.setTitle("This is the title")
      entry.setUpdated(new Date())
      entry.addAuthor("James");
      entry.setContent("This is the content");
      s.doc = s.abderaClient.post(s.collection.getResolvedHref.toString, entry).getDocument[Entry]
      s.doc must not beNull
    }

  }
}
