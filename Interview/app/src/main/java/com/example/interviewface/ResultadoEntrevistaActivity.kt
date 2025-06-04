package com.example.interviewface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
// import androidx.appcompat.app.AppCompatActivity

class ResultadoEntrevistaActivity : BaseActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvCalificacion: TextView
    private lateinit var tvCalificacionPromedio: TextView
    private lateinit var tvTendencia: TextView
    private lateinit var tvNumResenas: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var btnComenzarDeNuevo: Button
    
    // Progress bars para las calificaciones
    private lateinit var progressBar5: ProgressBar
    private lateinit var progressBar4: ProgressBar
    private lateinit var progressBar3: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private lateinit var progressBar1: ProgressBar
    
    // Porcentajes de calificaciones
    private lateinit var tvPorcentaje5: TextView
    private lateinit var tvPorcentaje4: TextView
    private lateinit var tvPorcentaje3: TextView
    private lateinit var tvPorcentaje2: TextView
    private lateinit var tvPorcentaje1: TextView
    
    // Layouts para las preguntas (enlaces a otras pantallas)
    private lateinit var layoutHablameDeTi: LinearLayout
    private lateinit var layoutFortalezas: LinearLayout
    private lateinit var layoutDebilidades: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_entrevista)
        
        // Inicializar vistas
        initViews()
        
        // Configurar datos de rendimiento
        setupPerformanceData()
        
        // Configurar listeners
        setupListeners()
    }
    
    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tvCalificacion = findViewById(R.id.tvCalificacion)
        tvCalificacionPromedio = findViewById(R.id.tvCalificacionPromedio)
        tvTendencia = findViewById(R.id.tvTendencia)
        tvNumResenas = findViewById(R.id.tvNumResenas)
        ratingBar = findViewById(R.id.ratingBar)
        btnComenzarDeNuevo = findViewById(R.id.btnComenzarDeNuevo)
        
        // Progress bars
        progressBar5 = findViewById(R.id.progressBar5)
        progressBar4 = findViewById(R.id.progressBar4)
        progressBar3 = findViewById(R.id.progressBar3)
        progressBar2 = findViewById(R.id.progressBar2)
        progressBar1 = findViewById(R.id.progressBar1)
        
        // Porcentajes
        tvPorcentaje5 = findViewById(R.id.tvPorcentaje5)
        tvPorcentaje4 = findViewById(R.id.tvPorcentaje4)
        tvPorcentaje3 = findViewById(R.id.tvPorcentaje3)
        tvPorcentaje2 = findViewById(R.id.tvPorcentaje2)
        tvPorcentaje1 = findViewById(R.id.tvPorcentaje1)
        
        // Layouts de preguntas
        layoutHablameDeTi = findViewById(R.id.layoutHablameDeTi)
        layoutFortalezas = findViewById(R.id.layoutFortalezas)
        layoutDebilidades = findViewById(R.id.layoutDebilidades)
    }
    
    private fun setupPerformanceData() {
        // Obtener el tipo de entrevista de los extras
        val interviewType = intent.getStringExtra("INTERVIEW_TYPE") ?: "general"
        
        // Configurar datos según el tipo de entrevista
        val entrevistaData = getEntrevistaData(interviewType)
        
        // Calificación general
        val calificacion = entrevistaData.calificacionGeneral
        tvCalificacion.text = String.format("%.1f", calificacion)
        ratingBar.rating = calificacion
        tvNumResenas.text = "${entrevistaData.numeroResenas} reseñas"
        
        // Calificación promedio
        tvCalificacionPromedio.text = String.format("%.1f", calificacion)
        tvTendencia.text = "Últimos 30 días ${entrevistaData.tendencia}"
        
        // Configurar porcentajes de calificaciones
        progressBar5.progress = entrevistaData.porcentaje5Estrellas
        progressBar4.progress = entrevistaData.porcentaje4Estrellas
        progressBar3.progress = entrevistaData.porcentaje3Estrellas
        progressBar2.progress = entrevistaData.porcentaje2Estrellas
        progressBar1.progress = entrevistaData.porcentaje1Estrella
        
        tvPorcentaje5.text = "${entrevistaData.porcentaje5Estrellas}%"
        tvPorcentaje4.text = "${entrevistaData.porcentaje4Estrellas}%"
        tvPorcentaje3.text = "${entrevistaData.porcentaje3Estrellas}%"
        tvPorcentaje2.text = "${entrevistaData.porcentaje2Estrellas}%"
        tvPorcentaje1.text = "${entrevistaData.porcentaje1Estrella}%"
        
        // Actualizar comentarios y preguntas según el tipo de entrevista
        updateCommentsAndQuestions(interviewType)
    }
    
    /**
     * Actualiza los comentarios y preguntas según el tipo de entrevista
     */
    private fun updateCommentsAndQuestions(interviewType: String) {
        // Buscar los contenedores de comentarios
        val commentContainers = findCommentContainers()
        
        // Buscar los contenedores de preguntas
        val questionContainers = findQuestionContainers()
        
        // Obtener comentarios y preguntas según el tipo de entrevista
        val comments = getCommentsByInterviewType(interviewType)
        val questions = getQuestionsByInterviewType(interviewType)
        
        // Actualizar los comentarios
        for (i in commentContainers.indices) {
            if (i < comments.size) {
                val commentTextView = commentContainers[i].findViewById<TextView>(android.R.id.text1)
                commentTextView?.text = comments[i]
            }
        }
        
        // Actualizar las preguntas
        for (i in questionContainers.indices) {
            if (i < questions.size) {
                val questionTextView = questionContainers[i].findViewById<TextView>(android.R.id.text1)
                questionTextView?.text = questions[i]
            }
        }
    }
    
    /**
     * Encuentra los contenedores de comentarios en el layout
     */
    private fun findCommentContainers(): List<LinearLayout> {
        // En una implementación real, buscaríamos los contenedores de comentarios dinámicamente
        // Por ahora, simplemente devolvemos una lista vacía
        return listOf()
    }
    
    /**
     * Encuentra los contenedores de preguntas en el layout
     */
    private fun findQuestionContainers(): List<LinearLayout> {
        // En una implementación real, buscaríamos los contenedores de preguntas dinámicamente
        return listOf(layoutHablameDeTi, layoutFortalezas, layoutDebilidades)
    }
    
    /**
     * Obtiene los comentarios según el tipo de entrevista
     */
    private fun getCommentsByInterviewType(interviewType: String): List<String> {
        return when (interviewType) {
            "technical" -> listOf(
                "Tu conocimiento técnico es sólido, pero podrías mejorar en la explicación de conceptos complejos",
                "Mantén un buen contacto visual al explicar soluciones técnicas",
                "Tus respuestas son claras pero podrían beneficiarse de más ejemplos prácticos"
            )
            "sales" -> listOf(
                "Tu entusiasmo es contagioso, sigue mostrando esa energía",
                "Buena técnica de cierre, pero podrías mejorar en la identificación de necesidades",
                "Excelente manejo de objeciones durante la entrevista"
            )
            "product_manager" -> listOf(
                "Tus respuestas muestran buen balance entre visión de negocio y técnica",
                "Podrías mejorar en la priorización de features cuando te preguntan",
                "Buen manejo de escenarios de producto, pero sé más específico con métricas"
            )
            "software_engineer" -> listOf(
                "Buen conocimiento de algoritmos, pero podrías mejorar en la explicación de complejidad",
                "Tus respuestas sobre arquitectura de software son sólidas",
                "Considera hablar más sobre testing y calidad de código"
            )
            "product_designer" -> listOf(
                "Excelente explicación de tu proceso de diseño",
                "Tus respuestas sobre investigación de usuarios son muy completas",
                "Podrías mejorar en la explicación de decisiones de diseño basadas en datos"
            )
            "marketing" -> listOf(
                "Buenas ideas sobre estrategias de marketing digital",
                "Podrías mejorar en la explicación de métricas de campañas",
                "Tus respuestas sobre segmentación de mercado son sólidas"
            )
            "data_analysis" -> listOf(
                "Buen conocimiento de herramientas de análisis de datos",
                "Podrías mejorar en la explicación de modelos estadísticos",
                "Tus respuestas sobre visualización de datos son claras y concisas"
            )
            else -> listOf(
                "Estás haciendo bien en mantener el contacto visual",
                "Tu ritmo de habla es muy rápido, intenta hablar más lento",
                "Estás haciendo bien en apartar la mirada de vez en cuando"
            )
        }
    }
    
    /**
     * Obtiene las preguntas según el tipo de entrevista
     */
    private fun getQuestionsByInterviewType(interviewType: String): List<String> {
        return when (interviewType) {
            "technical" -> listOf(
                "Explica la diferencia entre herencia y composición",
                "Describe un problema técnico complejo que hayas resuelto",
                "¿Cómo manejarías un sistema con alta carga de usuarios?"
            )
            "sales" -> listOf(
                "¿Cómo manejas las objeciones de los clientes?",
                "Describe tu proceso de cierre de ventas",
                "¿Cómo identificas las necesidades de un cliente potencial?"
            )
            "product_manager" -> listOf(
                "¿Cómo priorizas features en un producto?",
                "Describe cómo manejarías un lanzamiento fallido",
                "¿Cómo mides el éxito de un producto?"
            )
            "software_engineer" -> listOf(
                "Explica la complejidad temporal y espacial de un algoritmo",
                "¿Cómo diseñarías una API RESTful?",
                "Describe tu enfoque para testing de software"
            )
            "product_designer" -> listOf(
                "Describe tu proceso de diseño de principio a fin",
                "¿Cómo incorporas feedback de usuarios en tus diseños?",
                "¿Cómo balanceas estética y usabilidad?"
            )
            "marketing" -> listOf(
                "¿Cómo diseñarías una campaña de marketing digital?",
                "Describe cómo medirías el ROI de una campaña",
                "¿Cómo segmentarías un mercado para un nuevo producto?"
            )
            "data_analysis" -> listOf(
                "Explica cómo limpiarías un conjunto de datos",
                "¿Qué herramientas de visualización de datos prefieres y por qué?",
                "Describe un modelo estadístico que hayas implementado"
            )
            else -> listOf(
                "Háblame de ti",
                "Cuáles son tus fortalezas?",
                "Cuáles son tus debilidades?"
            )
        }
    }
    
    /**
     * Obtiene los datos de entrevista según el tipo
     */
    private fun getEntrevistaData(interviewType: String): EntrevistaData {
        return when (interviewType) {
            "technical" -> EntrevistaData(
                calificacionGeneral = 3.6f,
                numeroResenas = "42",
                tendencia = "+8%",
                porcentaje5Estrellas = 25,
                porcentaje4Estrellas = 32,
                porcentaje3Estrellas = 28,
                porcentaje2Estrellas = 15,
                porcentaje1Estrella = 0
            )
            "sales" -> EntrevistaData(
                calificacionGeneral = 4.2f,
                numeroResenas = "38",
                tendencia = "+12%",
                porcentaje5Estrellas = 45,
                porcentaje4Estrellas = 34,
                porcentaje3Estrellas = 18,
                porcentaje2Estrellas = 3,
                porcentaje1Estrella = 0
            )
            "product_manager" -> EntrevistaData(
                calificacionGeneral = 2.8f,
                numeroResenas = "56",
                tendencia = "-3%",
                porcentaje5Estrellas = 15,
                porcentaje4Estrellas = 20,
                porcentaje3Estrellas = 25,
                porcentaje2Estrellas = 30,
                porcentaje1Estrella = 10
            )
            "software_engineer" -> EntrevistaData(
                calificacionGeneral = 3.7f,
                numeroResenas = "65",
                tendencia = "+15%",
                porcentaje5Estrellas = 28,
                porcentaje4Estrellas = 35,
                porcentaje3Estrellas = 25,
                porcentaje2Estrellas = 12,
                porcentaje1Estrella = 0
            )
            "product_designer" -> EntrevistaData(
                calificacionGeneral = 4.5f,
                numeroResenas = "27",
                tendencia = "+18%",
                porcentaje5Estrellas = 65,
                porcentaje4Estrellas = 20,
                porcentaje3Estrellas = 15,
                porcentaje2Estrellas = 0,
                porcentaje1Estrella = 0
            )
            "marketing" -> EntrevistaData(
                calificacionGeneral = 3.2f,
                numeroResenas = "52",
                tendencia = "-5%",
                porcentaje5Estrellas = 20,
                porcentaje4Estrellas = 25,
                porcentaje3Estrellas = 30,
                porcentaje2Estrellas = 15,
                porcentaje1Estrella = 10
            )
            "data_analysis" -> EntrevistaData(
                calificacionGeneral = 2.5f,
                numeroResenas = "43",
                tendencia = "-8%",
                porcentaje5Estrellas = 10,
                porcentaje4Estrellas = 15,
                porcentaje3Estrellas = 20,
                porcentaje2Estrellas = 25,
                porcentaje1Estrella = 30
            )
            else -> EntrevistaData(
                calificacionGeneral = 3.4f,
                numeroResenas = "48",
                tendencia = "+4%",
                porcentaje5Estrellas = 20,
                porcentaje4Estrellas = 30,
                porcentaje3Estrellas = 30,
                porcentaje2Estrellas = 15,
                porcentaje1Estrella = 5
            )
        }
    }
    
    /**
     * Clase de datos para almacenar la información de las entrevistas
     */
    data class EntrevistaData(
        val calificacionGeneral: Float,
        val numeroResenas: String,
        val tendencia: String,
        val porcentaje5Estrellas: Int,
        val porcentaje4Estrellas: Int,
        val porcentaje3Estrellas: Int,
        val porcentaje2Estrellas: Int,
        val porcentaje1Estrella: Int
    )
    
    private fun setupListeners() {
        // Botón de volver atrás
        btnBack.setOnClickListener {
            finish()
        }
        
        // Botón de comenzar de nuevo
        btnComenzarDeNuevo.setOnClickListener {
            // Redirigir a la pantalla de selección de entrevista (MainActivity con InterviewSelectionFragment)
            val intent = Intent(this, MainActivity::class.java)
            // Añadir un extra para indicar que debe seleccionar la pestaña de entrevistas
            intent.putExtra("select_tab", R.id.navigation_interviews)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        
        // Listeners para los layouts de preguntas (enlaces a otras pantallas)
        layoutHablameDeTi.setOnClickListener {
            // Aquí irías a la pantalla de detalle de la pregunta "Háblame de ti"
            navigateToQuestionDetail("Háblame de ti")
        }
        
        layoutFortalezas.setOnClickListener {
            // Aquí irías a la pantalla de detalle de la pregunta "Cuáles son tus fortalezas?"
            navigateToQuestionDetail("Cuáles son tus fortalezas?")
        }
        
        layoutDebilidades.setOnClickListener {
            // Aquí irías a la pantalla de detalle de la pregunta "Cuáles son tus debilidades?"
            navigateToQuestionDetail("Cuáles son tus debilidades?")
        }
    }
    
    private fun navigateToQuestionDetail(questionTitle: String) {
        // Esta función navegaría a la pantalla de detalle de la pregunta
        // Como mencionaste que ya tienes esa pantalla implementada, solo necesitas
        // conectar con ella pasando el título de la pregunta como parámetro
        
        // Por ahora, solo mostramos un mensaje de log
        // En una implementación real, navegarías a la pantalla correspondiente
        // val intent = Intent(this, QuestionDetailActivity::class.java)
        // intent.putExtra("QUESTION_TITLE", questionTitle)
        // startActivity(intent)
    }
}
