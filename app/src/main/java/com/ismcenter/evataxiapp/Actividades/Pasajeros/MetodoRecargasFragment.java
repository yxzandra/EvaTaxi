package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ismcenter.evataxiapp.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.splunk.mint.Mint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MetodoRecargasFragment extends Fragment implements Validator.ValidationListener {

    Validator validator;
    Fragment frag;
    FragmentTransaction fragTansaction;
    Toolbar toolbar;



    @BindView(R.id.iv_deposito)
    CircleImageView  iv_deposito;

    @BindView(R.id.iv_trans)
    CircleImageView  iv_trans;

    @BindView(R.id.iv_card)
    CircleImageView  iv_card;

    @BindView(R.id.layou_deposito)
    LinearLayout layou_deposito;

    @BindView(R.id.layou_t)
    LinearLayout layou_t;

    @BindView(R.id.layou_tdc)
    LinearLayout layou_tdc;


    public static Fragment newInstance(String idPersona, String token) {
        return new MetodoRecargasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(getActivity(), "28d7577c");

        View view = inflater.inflate(R.layout.fragment_metodo_pago, container, false);
        ButterKnife.bind(this, view);

        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }


    @OnClick(R.id.layou_deposito)
    public void deposito() {

        frag = new DepositoSaldoFragment();
        frag = DepositoSaldoFragment.newInstance("", "");
        fragTansaction = getFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();

    }

    @OnClick(R.id.layou_t)
    public void trans(View view) {


        frag = new TransferenciaFragment();
        frag = TransferenciaFragment.newInstance("", "");
        fragTansaction = getFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();


    }

    @OnClick(R.id.layou_tdc)
    public void tdc(View view) {

        frag = new FragmentIstaPago();
        frag = FragmentIstaPago.newInstance("", "");
        fragTansaction = getFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();

    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getActivity(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            if (view instanceof RadioButton) {
                ((RadioButton) view).setError(message);
            }
            else
            {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }

    }



}
