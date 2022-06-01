package prog3.tpe.entrega1;

import java.io.*;
import java.util.LinkedList;

public class FileHandler {

    public static LinkedList<Libro> readFile(File pathFile) throws IOException {

        System.out.println("************");
        System.out.println("READING FILE: " + pathFile.getName());

        LinkedList<Libro> books = new LinkedList<Libro>();
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            //Salta la primer linea que contiene los titulos
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] items = line.split(csvSplitBy);
                Libro book = new Libro(items[0], items[1],items[2]);
                String[] genres = items[3].split(" ");
                for (String genre : genres) {
                    book.addGenero(genre);
                }
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Total elements: " + books.size());
        return books;
    }


    public static String convertToCSV(Libro data) {
        StringBuilder gens = new StringBuilder();
        for (String gen: data.getGeneros()) {
            gens.append(gen).append(" ");
        }
        return data.getAutor() + "," + data.getTitulo() + "," + data.getCantidadPaginas() + "," + gens;
    }


    public static void generateCsv(LinkedList<Libro> dataLines, File file) throws IOException {

        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            // Escribo la primer linea del archivo
            String headers = "Titulo,Autor,Paginas,Generos";
            bw.write(headers);
            bw.newLine();

            for (Libro book: dataLines) {
                String str = convertToCSV(book);
                bw.write(str);
                bw.newLine();
            }
            System.out.println("Output file: " + file.getName() +". Se guardo en la siguiente ruta: "+ file.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
