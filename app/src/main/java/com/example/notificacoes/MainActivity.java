package com.example.notificacoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Criar um ID para o canal de notificação (utilizado se for Android 8 ou acima)
    private final String ID_CANAL = "canalNotificacaoApp";
    //Objeto que irá receber o serviço de notificação do Android
    private NotificationManager servico;
    //Criação de código para identificar/representar a nossa notificação
    private final int CODIGO_NOTIFICACAO = 1458;//Qualquer número interiro

    //Método para verificar a versão do Android no dispositivo e criar o
    //canal de notificação caso seha Android 8 ou acima
    private  void criarCanalNotificacao(){
        //Verificar a versão do Android. Se a versão >- Android O (Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Para criar o canal é necessário um nome, descricao, ID e prioridade
            CharSequence nome = "Canal App Notificação";
            String descricao = "Canal utilizado pelo App Notificação";
            int prioridade = NotificationManager.IMPORTANCE_DEFAULT;
            //Criar o canal com as configurações montadas acima
            NotificationChannel canal = new NotificationChannel(
                    ID_CANAL, nome, prioridade);
            //Passar o canal criado para o serviço do Android
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarCanalNotificacao();

        Button buttonNotificar = findViewById(R.id.buttonNotificar);
        buttonNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Inciar o objeto "serviço"
                servico = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                Intent intent = new Intent(MainActivity.this, Tela2.class);
                intent.putExtra("abrirFragment", "Usuario");
                //Transformar a intent em PedingIntent
                PendingIntent p = PendingIntent.getActivity(
                        MainActivity.this, //Contexto
                        0, //Valor zero por padrão
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                //Configurar a notificação
                NotificationCompat.Builder notificacao =
                        new NotificationCompat.Builder(MainActivity.this, ID_CANAL)
                        .setContentTitle("Título da notificação")
                        .setContentText("Conteúdo de texto da notificação")
                        .setContentIntent(p)//PendingIntent
                .setAutoCancel(true)//Remove a notificação após clicar/tocar
                .setSmallIcon(R.drawable.icone_notificacao);
            }
        });
    }
}