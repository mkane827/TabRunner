package TabRunner



import org.junit.*
import grails.test.mixin.*

@TestFor(RoundController)
@Mock(Round)
class RoundControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/round/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.roundInstanceList.size() == 0
        assert model.roundInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.roundInstance != null
    }

    void testSave() {
        controller.save()

        assert model.roundInstance != null
        assert view == '/round/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/round/show/1'
        assert controller.flash.message != null
        assert Round.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/round/list'

        populateValidParams(params)
        def round = new Round(params)

        assert round.save() != null

        params.id = round.id

        def model = controller.show()

        assert model.roundInstance == round
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/round/list'

        populateValidParams(params)
        def round = new Round(params)

        assert round.save() != null

        params.id = round.id

        def model = controller.edit()

        assert model.roundInstance == round
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/round/list'

        response.reset()

        populateValidParams(params)
        def round = new Round(params)

        assert round.save() != null

        // test invalid parameters in update
        params.id = round.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/round/edit"
        assert model.roundInstance != null

        round.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/round/show/$round.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        round.clearErrors()

        populateValidParams(params)
        params.id = round.id
        params.version = -1
        controller.update()

        assert view == "/round/edit"
        assert model.roundInstance != null
        assert model.roundInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/round/list'

        response.reset()

        populateValidParams(params)
        def round = new Round(params)

        assert round.save() != null
        assert Round.count() == 1

        params.id = round.id

        controller.delete()

        assert Round.count() == 0
        assert Round.get(round.id) == null
        assert response.redirectedUrl == '/round/list'
    }
}
