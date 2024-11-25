package com.example.monprofil.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.monprofil.FilmsDestination
import com.example.monprofil.R


@Composable

fun ProfilScreen( navController: NavController) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MonImage()
                Texte()
                EmailWithIcon()
                LinkedInWithIcon()
                DemarrerButton(navController) // Passer le navController ici
            }
        }
        else -> {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MonImage()
                    Texte()
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        EmailWithIcon()
                        LinkedInWithIcon()
                    }
                    DemarrerButton(navController)
                }
            }
        }
    }
}



//@Composable
//fun Screen() {
//    Texte()
//    MonImage()
//    EmailWithIcon()
//    LinkedInWithIcon()
//    DemarrerButton()
//
//}


@Composable
fun MonImage() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .border(2.dp, Color.Black, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.moi),
            contentDescription = "moi",
            modifier = Modifier.size(150.dp) // Adjusted size for better fit
        )
    }
}

@Composable
fun Texte() {
    Column(
        Modifier
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SINDA BEN YAHMED",
            Modifier.padding(10.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Etudiante à ISIS et alternante chez Pierre Fabre",
            Modifier.padding(2.dp),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmailWithIcon() {
    Row(
        modifier = Modifier
            .padding(8.dp)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_email),
            contentDescription = "Email Icon",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            tint = Color(0xFF4682B4 )
        )
        Text(
            text = "sindabenyahmed@gmail.com",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun LinkedInWithIcon() {
    Row(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_linkedin),
            contentDescription = "linkedIn Icon",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            tint = Color(0xFF4682B4 )
        )
        Text(
            text = "linkedin.com/in/sinda-ben-yahmed/",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}

//@Composable
//fun DemarrerButton() {
//    Button(
//        onClick = { },
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
//        modifier = Modifier.padding(16.dp)
//    ) {
//        Text(
//            text = "Démarrer",
//            color = Color.White
//        )
//    }
//}
@Composable
fun DemarrerButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(FilmsDestination()) },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4682B4 )),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Démarrer",
            color = Color.White
        )
    }
}
