class UserAuthenticationSpec extends Specification {

    // se aplica principio de responsabilidad unica para cada prueba
    // se aplican una prueba para un happyPath
    // se aplica una prueaba para contraseña incorrecta
    // se aplica prueba para contraseña y usuarios vacios
    // se aplica prueba para cuando no se encuentra el usuario ( usuario inexistente)
    // se aplica prueba para casos nulos
    // se utilizan bloques descriptivos (given, when, then) para estructurar cada prueba.
    // Esta estructura es similar a BDD (Behavior-Driven Development) y ayuda a que cada parte del test sea clara y fácil de entender:
    // Los nombres de los métodos (should succeed with correct credentials, should fail with incorrect password, etc.) describen claramente el propósito de cada prueba.
    // Cobertura Completa de Casos de Error y Escenarios Límites (Edge Cases):
    //    Se agregaron pruebas adicionales para cubrir una variedad de errores comunes y escenarios límite, como entradas vacías, valores nulos y usuarios inexistentes.
    // Aunque cada prueba define valores específicos de username y password en el bloque given, los escenarios son suficientemente únicos como para justificar esta repetición.

    def "should authenticate successfully with correct credentials"() {

        given: "a valid username and password"
        String username = "validUser"
        String password = "validPass"

        when: "authenticate method is called with correct credentials"
        boolean result = authenticate(username, password)

        then: "authentication should be successful"
        result == true
    }

    def "should fail authentication with incorrect password"() {

        given: "a valid username and an incorrect password"
        String username = "validUser"
        String password = "wrongPass"

        when: "authenticate method is called with incorrect password"
        boolean result = authenticate(username, password)

        then: "authentication should fail"
        result == false
    }

    def "should fail with empty username and password"() {
        given: "empty username and password"
        String username = ""
        String password = ""

        when: "the user tries to authenticate"
        boolean result = authenticate(username, password)

        then: "the authentication should fail"
        result == false
    }

    def "should fail with non-existent username"() {

        given: "a username that does not exist and a valid password format"
        String username = "unknownUser"
        String password = "anyPassword"

        when: "the user tries to authenticate"
        boolean result = authenticate(username, password)

        then: "the authentication should fail"
        result == false
    }

    def "should fail with null username and password"() {
        given: "null username and password"
        String username = null
        String password = null

        when: "the user tries to authenticate"
        boolean result = authenticate(username, password)

        then: "the authentication should fail"
        result == false
    }
}


//-------------------------------------------------------------------------------------
//
//-------------------------------------------------------------------------------------

// se aplican principio de responsabilidad unica
// se aplica caso happypath
// se aplica caso de error
// se aplica caso de datos nulos
// se aplica caso de datos vacios
// Simulación de Excepciones con thrown() con lo cual nos podemos ahorrar un try cath


class DataProcessingSpec extends Specification {

//fetchData(): Simula la obtención de un objeto Data válido, que en este caso tiene valid: true.
    def "should process data successfully"() {
        given: "a valid data object"
        Data data = fetchData()

        when: "the data is processed"
        processData(data)

        then: "the data should be marked as processed successfully"
        data.processedSuccessfully == true
    }

    //fetchInvalidData(): Simula la obtención de un objeto Data inválido.
    //DataProcessingException representa una excepción personalizada que puede ser lanzada

    def "should handle data processing errors gracefully"() {
        given: "an invalid or problematic data object that causes processing error"
        Data data = fetchInvalidData()

        when: "an attempt to process the data is made"
        processData(data)

        then: "a DataProcessingException should be thrown"
        def exception = thrown(DataProcessingException)
        exception.message == "Data processing error"
    }

    def "should handle null data"() {
        given: "null data input"
        Data data = null

        when: "an attempt to process null data is made"
        processData(data)

        then: "a NullPointerException should be thrown or handled"
        thrown(NullPointerException)
    }

    def "should handle empty data"() {
        given: "an empty data object"
        Data data = new Data(valid: true, content: "")

        when: "the data is processed"
        processData(data)

        then: "the data should still be marked as processed successfully"
        data.processedSuccessfully == true
    }


}

//-------------------------------------------------------------------------------------
//
//-------------------------------------------------------------------------------------


class UIResponsivenessSpec extends Specification {


//Su usa un bloque expect pra evaluar direferntes inserciones
// Con esto se busca que en una prueba se puedan probar diferentes dimenciones de pantalla
    // every en grovee nos permite aplicar un bloque se codigo a todo el expect y si en todos los casos fueron exitosos la prueba fue exitosa
    // en este caso se puede agregar mas dimencines al bloque expect
    def "UI should adjust to different screen widths"() {
        given: "a UI component"
        UIComponent uiComponent = setupUIComponent()

        expect: "the UI component adjusts correctly to various screen widths"
        [
                320,   // Mobile
                768,   // Tablet (portrait)
                1024,  // Tablet (landscape)
                1440,  // Desktop standard
                1920   // Desktop large
        ].every { width ->
            uiComponent.adjustsToScreenSize(width)
        }
    }

    //en esta prueba se busca unicamente que el con la dimencion de 5000 no debe ser soportada
    def "UI should not adjust to unsupported screen width"() {
        given: "a UI component"
        UIComponent uiComponent = setupUIComponent()

        when: "an unsupported screen width is tested"
        boolean adjusts = uiComponent.adjustsToScreenSize(5000)  // Example of an unsupported width

        then: "the UI component should not adjust"
        adjusts == false
    }
}