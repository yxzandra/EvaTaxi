package com.ismcenter.evataxiapp.Actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.DatosBancariosAdapter;
import com.ismcenter.evataxiapp.Dialog.AddAccountBankDialog;
import com.ismcenter.evataxiapp.Dialog.ModifyAccountBankDialog;
import com.ismcenter.evataxiapp.Modelos.ArregloDatosBancarios;

import com.ismcenter.evataxiapp.Modelos.DataBanco;
import com.ismcenter.evataxiapp.Modelos.DataDatosBancarios;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseBankAccounts;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.OnItemClickAccountBank;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatosBancariosFragment extends Fragment implements OnItemClickAccountBank {
    public static String idUsuarioFragment,token, idPersonaFragment;
    @BindView(R.id.textNotieneBancos)
    TextView textNotieneBancos;
    @BindView(R.id.recyclerContainer)
    CoordinatorLayout recyclerContainer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ProgressDialog loading ;
    ArrayList<ArregloDatosBancarios> arregloDatosBancarios;
    List<String> nombreBancos;
    public static List<DataBanco> bancoModel;
    Activity activity;
    int finLoading=0;

    public DatosBancariosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        consultarBancosPersonas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos_bancarios, container, false);
        ButterKnife.bind(this, view);

        PreferencesManager pm = new PreferencesManager(getContext());
        idUsuarioFragment = pm.getUserId();
        idPersonaFragment = pm.getPersonID();
        token = pm.getServerToken();

        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        consultarBancosPersonas();
        return view;
    }

    public void consultarBancosPersonas() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+token)
                        .build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        //declaromos la interfaz

        UserMagnagementIterface servicio = retrofit.create(UserMagnagementIterface.class);
        int idUsuario = Integer.parseInt(idUsuarioFragment);
        servicio.bankAccounts(idUsuario).enqueue(new Callback<List<ResponseBankAccounts>>() {
            @Override
            public void onResponse(Call<List<ResponseBankAccounts>> call, Response<List<ResponseBankAccounts>> response) {
                if (finLoading == 2) {
                    loading.dismiss();
                    finLoading = 0;
                }else{
                    finLoading+=1;
                }
                if (response.code()==200){
                    textNotieneBancos.setVisibility(View.GONE);
                    listarDatosBancos(response.body());

                }if (response.body().size() ==0){
                    textNotieneBancos.setVisibility(View.VISIBLE);
               }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"));
                        textNotieneBancos.setText(jObjError.getString("message"));
                    } catch (Exception e) {
//                        Log.e("respuestaServicio", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseBankAccounts>> call, Throwable t) {
                textNotieneBancos.setVisibility(View.VISIBLE);
                textNotieneBancos.setText("No hubo respuesta del servidor");
                if (finLoading == 2) {
                    loading.dismiss();

                    finLoading = 0;
                }else{
                    finLoading+=1;
                }
            }
        });

        servicio.getAllBank().enqueue(new Callback<List<DataBanco>>() {
            @Override
            public void onResponse(Call<List<DataBanco>> call, Response<List<DataBanco>> response) {
                if (finLoading == 2) {
                    loading.dismiss();
                    finLoading = 0;
                }else{
                    finLoading+=1;
                }
                if (response.code()==200){
                    bancoModel = response.body();
                    nombreBancos = new ArrayList<>();

                    for (DataBanco banco : bancoModel) {
                        nombreBancos.add(banco.getBanNombre());
                    }

                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"));
                    } catch (Exception e) {
                        respuestaServicio(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DataBanco>> call, Throwable t) {
                textNotieneBancos.setVisibility(View.VISIBLE);
                textNotieneBancos.setText("No hubo respuesta del servidor");
                if (finLoading == 2) {
                    loading.dismiss();
                    finLoading = 0;
                }else{
                    finLoading+=1;
                }
            }
        });

    }

    private void listarDatosBancos(List<ResponseBankAccounts> body) {
        arregloDatosBancarios = new ArrayList<>();
        String nombreTipoCuenta;
        for (ResponseBankAccounts body1 : body) {
            if (body1.getDabTipoCuenta().equals("1")) {
                nombreTipoCuenta = "Corriente";
            }else {
                nombreTipoCuenta = "Ahorro";
            }

            ArregloDatosBancarios arregloDatoBancarioSolo = new ArregloDatosBancarios(
                    body1.getDabBanco(), nombreTipoCuenta,
                    body1.getDabCuenta(), body1.getDabNombre(),
                    body1.getDabId());

            arregloDatosBancarios.add(arregloDatoBancarioSolo);
        }

            recyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
            recyclerView.setLayoutManager(gridLayoutManager);

            recyclerView.setAdapter(new DatosBancariosAdapter(arregloDatosBancarios,this));

        }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        AddAccountBankDialog alertdFragment = new AddAccountBankDialog();
        AddAccountBankDialog.newInstance(idUsuarioFragment, nombreBancos,token, idPersonaFragment);
        showDialogFragment(alertdFragment);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void actualizarLista(){
        consultarBancosPersonas();
    }

    @Override
    public void onItemClicked(int position) {
        ModifyAccountBankDialog alertdFragment = new ModifyAccountBankDialog();
        String idCuenta = String.valueOf(arregloDatosBancarios.get(position).getDab_id());
        int tipoCuenta;

        if (arregloDatosBancarios.get(position).getTipoCuenta().equals("Corriente")){
            tipoCuenta = 1;
        }else {
            tipoCuenta = 2;
        }
        DataDatosBancarios dataDatosBancarios = new DataDatosBancarios(Integer.parseInt(idUsuarioFragment),
                idCuenta,arregloDatosBancarios.get(position).getNumeroCuenta(),tipoCuenta,
                arregloDatosBancarios.get(position).getNombrePersona(),Integer.parseInt(idPersonaFragment));

        ModifyAccountBankDialog.newInstance(nombreBancos,token,dataDatosBancarios,arregloDatosBancarios.get(position));
        showDialogFragment(alertdFragment);
    }
}
