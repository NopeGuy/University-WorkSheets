����   < �
      java/lang/Object <init> ()V	  	 
   	Encomenda nomeCliente Ljava/lang/String;	     nif I	     morada	     numeroEncomenda	     data Ljava/time/LocalDate;
     setEncomendas (Ljava/util/ArrayList;)V
   ! " # $ java/time/LocalDate now ()Ljava/time/LocalDate; & java/util/ArrayList	  ( ) * 
encomendas Ljava/util/ArrayList;
 % , - . size ()I
 % 0  1 (I)V
  3 4 5 getNomeCliente ()Ljava/lang/String;
  7 8 . getNif
  : ; 5 	getMorada
  = > . getNumeroEncomenda
  @ A $ getData
  C D E getEncomendas ()Ljava/util/ArrayList;
 % 
 % H I J iterator ()Ljava/util/Iterator; L M N O P java/util/Iterator hasNext ()Z L R S T next ()Ljava/lang/Object; V LinhaEncomenda
 U X Y Z clone ()LLinhaEncomenda;
 % \ ] ^ add (Ljava/lang/Object;)Z ` java/lang/StringBuilder
 _  c Nome de cliente = '
 _ e f g append -(Ljava/lang/String;)Ljava/lang/StringBuilder; i '|  k NIF = 
 _ m f n (I)Ljava/lang/StringBuilder; p |  r 
Morada = ' t Número de encomenda =  v Data = 
   x y 5 toString { 
  } 
Detalhes da encomenda = 
 % x �  
 _ � f � (C)Ljava/lang/StringBuilder;
 _ x
  � � � getClass ()Ljava/lang/Class;
 % � � ^ equals
  �  � (LEncomenda;)V
 U � � � calculaValorLinhaEnc ()D
 U � � � calculaValorDesconto
 U � � . getQuantidade
 U � � 5 getReferencia
 � � � java/lang/String
 % � � ^ remove
  � Y � ()LEncomenda; 	Signature 'Ljava/util/ArrayList<LLinhaEncomenda;>; S(Ljava/lang/String;ILjava/lang/String;ILjava/time/LocalDate;Ljava/util/ArrayList;)V Code LineNumberTable LocalVariableTable this LEncomenda; LocalVariableTypeTable e(Ljava/lang/String;ILjava/lang/String;ILjava/time/LocalDate;Ljava/util/ArrayList<LLinhaEncomenda;>;)V ec setNomeCliente (Ljava/lang/String;)V setNif 	setMorada setNumeroEncomenda setData (Ljava/time/LocalDate;)V )()Ljava/util/ArrayList<LLinhaEncomenda;>; le LLinhaEncomenda; StackMapTable *(Ljava/util/ArrayList<LLinhaEncomenda;>;)V sb Ljava/lang/StringBuilder; o Ljava/lang/Object; calculaValorTotal vt D vc numeroTotalProdutos ntp existeProdutoEncomenda (Ljava/lang/String;)Z 
refProduto res Z adicionaLinha (LLinhaEncomenda;)V linha removeProduto codProd 
Exceptions � $java/lang/CloneNotSupportedException 
SourceFile Encomenda.java !                                ) *  �    �    �  �   �     &*� *+� *� *-� *� *� *� �    �   "       	          %  �   H    & � �     &      &      &      &      &      & ) *  �       & ) �  �    �     �   x     2*� *� *� *� *� *� � *� %Y*� '� +� /� '�    �   "    !  " 	 #  $  %  &  ' 1 ( �       2 � �     �  �   �     5*� *+� 2� *+� 6� *+� 9� *+� <� *+� ?� *+� B� �    �   "    .  /  0  1  2 $ 3 , 4 4 5 �       5 � �     5 � �   4 5  �   /     *� �    �       < �        � �    � �  �   >     *+� �    �   
    D  E �        � �          8 .  �   /     *� �    �       L �        � �    � 1  �   >     *� �    �   
    T  U �        � �          ; 5  �   /     *� �    �       \ �        � �    � �  �   >     *+� �    �   
    d  e �        � �          > .  �   /     *� �    �       l �        � �    � 1  �   >     *� �    �   
    t  u �        � �          A $  �   /     *� �    �       | �        � �    � �  �   >     *+� �    �   
    �  � �        � �          D E  �   /     *� '�    �       � �        � �   �    �     �   �     3*� %Y� F� '+� GM,� K � ,� Q � UN*� '-� W� [W���    �       �  � # � / � 2 � �      #  � �    3 � �     3 ) *  �       3 ) �  �    �  L� ! �    �  y 5  �   �     �� _Y� aL+b� d*� � dh� dW+j� d*� � lo� dW+q� d*� � dh� dW+s� d*� � lo� dW+u� d*� � w� dz� dW+|� d*� '� ~� d� dW+
� �W+� ��    �   & 	   �  �  � . � A � T � j � � � � � �       � � �    � � �   � ^  �   �     h*+� �+� *� �+� �� �+� M*� ,� � A*� ,� � 6*� ,� � +*� ,� �  *� ,� � *� ',� '� �� � �    �   "    �  �  �  �  �  � \ � g � �        h � �     h � �   K � �  �    � M @  Y �  �   3     	� Y*� ��    �       � �       	 � �    � �  �   �     +H*� '� GN-� K � -� Q � U:'� �cH���'�    �       �  �  � ) � �        � �    + � �    ) � �  �    � 
 L�   � �  �   �     +H*� '� GN-� K � -� Q � U:'� �cH���'�    �       �  �  � ) � �        � �    + � �    ) � �  �    � 
 L�   � .  �   �     )<*� '� GM,� K � ,� Q � UN-� �`<����    �       �  �  � ' � �        � �    ) � �    ' �   �    � 
 L�   � �  �   �     1=*� '� GN-� K � -� Q � U:+� �� �� =����    �       �  �  � * � , � / � �   *    � �    1 � �     1 �    / � �  �    � 
 L!�   � �  �   B     
*� '+� [W�    �   
    � 	 � �       
 � �     
 � �   � �  �   �     3*� '� GM,� K � $,� Q � UN+-� �� �� *� '-� �W��ٱ    �       �  � & � / � 2  �        � �    3 � �     3 �   �    �  L&� A Y T  �   /     *� ��    �        �        � �   �     �  �    �