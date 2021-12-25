/** Persistência de objetos:
 * Permite não realizar operações de plano de dados diretamente.
 *  você cria objetos que representam itens em índices e tabelas do DynamoDB
 *  e interage apenas com esses objetos. Isso permite que você escreva um código
 *  centrado em objetos, em vez de um código centrado em banco de dados.
 */
package com.github.bcmes.dynamodb.api.highlevel;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Music")
public class Aws01MusicORM {
    private String artist;
    private String songTitle;
    private String albumTitle;
    private int year;

    @DynamoDBHashKey(attributeName="Artist")
    public String getArtist() { return artist;}
    public void setArtist(String artist) {this.artist = artist;}

    @DynamoDBRangeKey(attributeName="SongTitle")
    public String getSongTitle() { return songTitle;}
    public void setSongTitle(String songTitle) {this.songTitle = songTitle;}

    @DynamoDBAttribute(attributeName = "AlbumTitle")
    public String getAlbumTitle() { return albumTitle;}
    public void setAlbumTitle(String albumTitle) {this.albumTitle = albumTitle;}

    @DynamoDBAttribute(attributeName = "Year")
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
