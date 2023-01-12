/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_119.finalproject_119;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Microsoft
 */
@RestController
@CrossOrigin
@RequestMapping("/data")
public class mycontroller {

    Datamahasiswa mydata = new Datamahasiswa();
    DatamahasiswaJpaController ctrl = new DatamahasiswaJpaController();

    @GetMapping("/{id}")
    public List<Datamahasiswa> getNameById(@PathVariable("id") int id) {
        List<Datamahasiswa> dummy = new ArrayList<>(); // deklarasi list baru
        try {
            mydata = ctrl.findDatamahasiswa(id); //mendapatkan data dari db
            dummy.add(mydata);//isi data dari db ke list
        } catch (Exception e) {
            dummy = List.of(); //data tidak ditemukan
        }
        return dummy;
    }

    @GetMapping
    public List<Datamahasiswa> getAll() {
        List<Datamahasiswa> dummy = new ArrayList<>();// deklarasi list baru
        try {//mengambil daftar objek Barang
            dummy = ctrl.findDatamahasiswaEntities();
        } catch (Exception e) {
            dummy = List.of();////data tidak ditemukan
        }
        return dummy;
    }

    @PostMapping()
    //metode yang menggunakan parameter tunggal bertipe HttpEntity<String> bernama 'paket'.
            //Itu kemudian mendeklarasikan variabel baru bernama 'message' sebagai tipe String dan memberikan string kosong padanya.
    public String createData(HttpEntity<String> paket) {
        String message = "";

        try {
            //membuat variabel bernama json_receive dan menugaskannya sebagai hasil dari pemanggilan metode getBody() 
            //pada paket HttpEntity. Metode getBody() kdigunakan untuk mengakses isi permintaan
            String json_receive = paket.getBody();
            //membuat objek ObjectMapper baru, 
            //digunakan untuk mengonversi objek Java ke JSON.
            ObjectMapper map = new ObjectMapper();
            //membuat objek baru 
            Datamahasiswa data = new Datamahasiswa();
            //meneruskan variabel json_receive dan objek kelas Datamahasiswa. Metode ini memetakan JSON ke objek.
            data = map.readValue(json_receive, Datamahasiswa.class);
            //membuat instance baru Barang menggunakan data yang diperoleh dari json_receive
            ctrl.create(data);
            //memanggil metode create() pada objek ctrl yang telah ditentukan meneruskan instance Datamahasiswa baru +memberikan pesan data tersimpan
            message = data.getName() + " Data tersimpan";
            //menangkap eror
        } catch (Exception e) {
            message = "Gagal";
        }

        return message;
    }

    @PutMapping()
    public String editData(HttpEntity<String> kiriman) {
        String message = "tidak ada tindakan";
        try {
            String json_receive = kiriman.getBody();
            ObjectMapper mapper = new ObjectMapper();
            ////membuat objek baru 
            Datamahasiswa newdatas = new Datamahasiswa();

            newdatas = mapper.readValue(json_receive, Datamahasiswa.class);
            ctrl.edit(newdatas);
            message = newdatas.getName() + " data telah diperbarui";
        } catch (Exception e) {
        }
        return message;
    }

    @DeleteMapping()
    public String deleteData(HttpEntity<String> kiriman) {
        String message = "tidak ada tindakan";
        try {
            String json_receive = kiriman.getBody();
            ObjectMapper mapper = new ObjectMapper();

            Datamahasiswa newdatas = new Datamahasiswa();

            newdatas = mapper.readValue(json_receive, Datamahasiswa.class);
            ctrl.destroy(newdatas.getId());

            message = "Data telah Dihapus";
        } catch (Exception e) {
        }
        return message;
    }

}
