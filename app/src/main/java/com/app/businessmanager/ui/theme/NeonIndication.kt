package com.app.businessmanager.ui.theme


import androidx.compose.animation.core.*
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch

data class NeonIndication(private val shape: Shape, private val borderWidth: Dp) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return NeonNode(shape, borderWidth, interactionSource)
    }
}

private class NeonNode(
    private val shape: Shape,
    private val borderWidth: Dp,
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifierNode {

    private val infiniteProgress = Animatable(0f)
    private val pressAlpha = Animatable(0.5f)

    override fun onAttach() {
        coroutineScope.launch {
            infiniteProgress.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> pressAlpha.animateTo(1f, tween(200))
                    is PressInteraction.Release, is PressInteraction.Cancel -> pressAlpha.animateTo(0.5f, tween(400))
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        drawContent()
        val progress = infiniteProgress.value
        val alpha = pressAlpha.value

        val colors = listOf(
            Color(0xFF30C0D8), Color(0xFF7848A8), Color(0xFFF03078),
            Color(0xFFF07800), Color(0xFF30C0D8)
        )

        val xOffset = size.width * progress
        val brush = Brush.linearGradient(
            colors = colors,
            start = Offset(xOffset - size.width, 0f),
            end = Offset(xOffset, 0f),
            tileMode = TileMode.Repeated
        )

        val outline = shape.createOutline(size, layoutDirection, this)
        drawOutline(
            outline = outline,
            brush = brush,
            alpha = alpha,
            style = Stroke(width = borderWidth.toPx())
        )
    }
}