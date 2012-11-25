package TabRunner



import org.junit.*
import grails.test.mixin.*

@TestFor(BallotController)
@Mock(Ballot)
class BallotControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/ballot/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.ballotInstanceList.size() == 0
        assert model.ballotInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.ballotInstance != null
    }

    void testSave() {
        controller.save()

        assert model.ballotInstance != null
        assert view == '/ballot/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/ballot/show/1'
        assert controller.flash.message != null
        assert Ballot.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/ballot/list'

        populateValidParams(params)
        def ballot = new Ballot(params)

        assert ballot.save() != null

        params.id = ballot.id

        def model = controller.show()

        assert model.ballotInstance == ballot
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/ballot/list'

        populateValidParams(params)
        def ballot = new Ballot(params)

        assert ballot.save() != null

        params.id = ballot.id

        def model = controller.edit()

        assert model.ballotInstance == ballot
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/ballot/list'

        response.reset()

        populateValidParams(params)
        def ballot = new Ballot(params)

        assert ballot.save() != null

        // test invalid parameters in update
        params.id = ballot.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/ballot/edit"
        assert model.ballotInstance != null

        ballot.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/ballot/show/$ballot.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        ballot.clearErrors()

        populateValidParams(params)
        params.id = ballot.id
        params.version = -1
        controller.update()

        assert view == "/ballot/edit"
        assert model.ballotInstance != null
        assert model.ballotInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/ballot/list'

        response.reset()

        populateValidParams(params)
        def ballot = new Ballot(params)

        assert ballot.save() != null
        assert Ballot.count() == 1

        params.id = ballot.id

        controller.delete()

        assert Ballot.count() == 0
        assert Ballot.get(ballot.id) == null
        assert response.redirectedUrl == '/ballot/list'
    }
}
